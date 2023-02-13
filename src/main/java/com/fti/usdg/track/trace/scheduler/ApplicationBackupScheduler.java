/**
 * 
 */
package com.fti.usdg.track.trace.scheduler;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



/**
 * @author Anup Kumar Gupta
 *
 */

@Component
public class ApplicationBackupScheduler {

	static Integer backupRotationCounter = 1;
	
	@Autowired
	ApplicationUtilMethods ApplicationUtilMethods = null;

	private static final Logger logger = LoggerFactory.getLogger(ApplicationBackupScheduler.class);
	@Value("${backup-s3-bucket-name}")
	private String bucketName;
 
	@Value("${fabric-network-deployment-dir}")
	private String fabNetworkDeploymentDir;

	@Value("${fabric-network-deployment-path}")
	private String fabNetworkDeploymentPath;
	
	@Value("${fabric-network-backup-path}")
	private String backupLocation;
 
	 
	/**
	 * @return
	 * @throws IOException
	 */
	@Scheduled(cron = "${backup.job.time.interval}")
	public String copyFabricNatworkDataToS3() throws IOException {
		logger.info(" copyFabricNatworkDataToS3 starts ");
		String response = "Successfully backup taken";
		String result = null;
		File hlfDir = new File(fabNetworkDeploymentPath);
		logger.info("start copyFabricNatworkDataToS3 " + hlfDir.exists());
		if (true/* hlfDir.exists() */) {
			StringBuilder command = new StringBuilder();
			command.append("cd  " + hlfDir.getAbsolutePath()).append("\n");
			command.append("cd ..").append("\n");
 			String tarFileName = "ftitt-fab-network-data-backup" + backupRotationCounter + ".tar.gz";
			command.append("sudo tar -czvf " + tarFileName + " "+fabNetworkDeploymentDir).append("\n");
			command.append("minsize=5242880").append("\n");
			command.append("filesize=$(stat -c%s " + tarFileName + ")").append("\n");
			command.append("echo \"Size of $filename = $filesize bytes.\"").append("\n");
			command.append("aws s3 cp " + tarFileName + " s3://" + bucketName).append("\n");
			//command.append("sudo mv " + tarFileName +" "+backupLocation).append("\n");
			command.append("sudo rm " + tarFileName).append("\n");
			logger.info("command \n" + command);
			result = takeBackupAndUpload(command.toString()) + "_ FileName ;" + tarFileName;
			logger.info(" Upload Result " + result);
			if (backupRotationCounter == 21) {
				backupRotationCounter = 1;
			}
			backupRotationCounter++;
			logger.info(" copyFabricNatworkDataToS3 : " + command);
			logger.info(" Backup stored in S3 Bucket : " + tarFileName + " s3://" + bucketName);
		}
		return response + " " + result;
	}

	public String takeBackupAndUpload(String fileContent) {
		int status = 0;
		try {
			String scriptFileLoc = File.separator + "tmp" + File.separator + "scriptFile.sh";
			ApplicationUtilMethods.writeFile(scriptFileLoc, fileContent);
			Process process = Runtime.getRuntime().exec("/bin/bash " + scriptFileLoc);
			new ApplicationSchedulerThread(process.getErrorStream()).start();
			new ApplicationSchedulerThread(process.getInputStream()).start();
			status = process.waitFor();
			if (status == 0) {
				logger.info("exit success");
			} else {
				logger.info("exit fail");
			}
		} catch (Exception e) {
			System.out.println("exception occurs......");
			e.printStackTrace();
		}
		return String.valueOf(status);
	}

	/**
	 * @param requestObject
	 * @return
	 */
	 public String restoreBackup(Map<String, String> requestObject) {
		logger.debug("Entering into restoreBackup " + bucketName);
		String response = "Successfully restored";
		ApplicationUtilMethods UtilityMethods = new ApplicationUtilMethods();
		String wholeNetworkBackName = "cama-fab-network-data-backup-all.tar.gz";
		StringBuilder command = new StringBuilder("cd ~").append("\n");
		command.append("mkdir -p " + fabNetworkDeploymentPath).append("\n");
		command.append("cd " + fabNetworkDeploymentPath).append("\n");
		command.append("cd ..").append("\n");
		command.append("aws s3 ls " + bucketName + " --recursive | sort | tail -n 1 | awk '{print $4}'").append("\n");
		String latestBackupFileName = UtilityMethods.writeShellScriptNExecute(command.toString());
		command = new StringBuilder("cd " + fabNetworkDeploymentPath).append("\n").append("\n");
		command.append("cd ..").append("\n");
		command.append(" aws s3 cp s3://" + bucketName + "/" + wholeNetworkBackName + " .").append("\n");
		command.append(" sleep 5").append("\n");
		command.append(" tar -xzf " + wholeNetworkBackName).append("\n");
		command.append("\n");
		logger.debug(" Initial file copied ");
		UtilityMethods.writeShellScriptNExecute(command.toString());
		logger.debug(
				"name of latest file latestBackupFileName " + latestBackupFileName + latestBackupFileName.length());
		if (latestBackupFileName != null && latestBackupFileName.length() > 0) {
			logger.info("latestBackupFileName " + latestBackupFileName);
			command = new StringBuilder("cd " + fabNetworkDeploymentPath).append("\n");
			command.append(" aws s3 cp s3://" + bucketName + "/" + latestBackupFileName + " .").append("\n");
			command.append(" sleep 5").append("\n");
			command.append(" tar -xzf " + latestBackupFileName).append("\n");
			// command.append(" rm -rf " + latestBackupFileName).append("\n");
			logger.debug("command " + command);
			response = ApplicationUtilMethods.writeShellScriptNExecute(command.toString());
			response = response + "\n" + latestBackupFileName;
			response = "Successfully restored " + latestBackupFileName + "\n";
			command = new StringBuilder("\n");
		} else {
			response = "Failed : File does not exist or unable to download ";
		}
		return response + command;
	}
 
 
}
