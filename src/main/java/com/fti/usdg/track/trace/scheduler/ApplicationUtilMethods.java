/**
 * 
 */
package com.fti.usdg.track.trace.scheduler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author Anup Kumar Gupta
 *
 */
@Component
public class ApplicationUtilMethods {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationUtilMethods.class);

	public static String readFile(String filePath) throws IOException {
		logger.debug("UtilityMethods readFile method called filePath --> " + filePath);
		String content = "";
		content = new String(Files.readAllBytes(Paths.get(filePath, new String[0])));
		logger.debug("Leaving from readFile Done " + content);
		return content;
	}

	public String writeShellScriptNExecute(String scriptFileContent) {
		logger.debug(" Entering into writeShellExecute :-\n" + scriptFileContent);
		Runtime runtime = Runtime.getRuntime();
		String result = "500";
		String uuid = String.valueOf(UUID.randomUUID());
		String scriptFileLoc = File.separator + "tmp" + File.separator + uuid + "scriptFile.sh";
		try {
			writeFile(scriptFileLoc, scriptFileContent);
			logger.debug("Going to execute :-" + scriptFileLoc);
			Process p1 = runtime.exec("/bin/bash " + scriptFileLoc);
			logger.debug(" Executed Successfully  :-");
			result = printResults(p1);
		} catch (IOException ioException) {
			logger.error(" Error in publish method " + ioException.getMessage());
			logger.debug(ioException.getMessage());
			result = String.valueOf("500 " + ioException.getMessage());
		}
		logger.debug(" Leaving from writeShellScriptNExecute :-" + result.toString());
		new File(scriptFileLoc).delete();
		return result.toString();
	}

	public void writeFile(String fileNameWihPath, String fileContent) {
		logger.info("Entering into writeFile " + fileNameWihPath);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameWihPath));
			writer.write(fileContent);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("Exiting into writeFile Done " + fileNameWihPath);
	}

	public static String printResults(Process process) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = "";
		while ((line = reader.readLine()) != null) {
			System.out.println(line);
			sb.append(line);
		}
		return sb.toString();
	}
}
