package com.fti.usdg.track.trace.common;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFileFromDevice(MultipartFile file) {
    	logger.debug("Entering into storeFileFromDevice " );
        String imageFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(imageFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + imageFileName);
            }
            
            String triplanNo = imageFileName.substring(0,imageFileName.indexOf("_"));
    		String orderNo = imageFileName.substring(imageFileName.lastIndexOf("_")+1,imageFileName.indexOf("."));
    		boolean orderPresent = false;
    		if (Pattern.matches("[a-zA-Z]+", orderNo) == false && orderNo.length() > 2) {
    			orderPresent = true;
    		}
    		String activityName = null;
    		logger.debug("\n"+triplanNo + "\n" + orderNo +"\n"+activityName);
    		
    		String createDir = File.separator+triplanNo;
    		/*if(orderPresent) {
    			activityName = imageFileName.substring(imageFileName.indexOf("_")+1,imageFileName.indexOf(orderNo)-1);
    			createDir = createDir+File.separator+orderNo;
    		}else {
    			activityName = imageFileName.substring(imageFileName.indexOf("_")+1);
    		}
    		String imageFileExt = imageFileName.substring(imageFileName.indexOf("."));*/
    		
    		createDir = fileStorageLocation.toAbsolutePath()+File.separator+triplanNo+File.separator;
    		File uploadFileDir = new File(createDir);
    		uploadFileDir.mkdirs();
    		
    		//createDir = createDir+activityName+imageFileExt;
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation =  this.fileStorageLocation.resolve(createDir+imageFileName);
            logger.debug(" createDir "+createDir);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return imageFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + imageFileName + ". Please try again!", ex);
        }
    }
    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
        	String triplanNo = fileName.substring(0,fileName.indexOf("_"));
        	fileName = triplanNo+File.separator+fileName;
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundCustomException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundCustomException("File not found " + fileName, ex);
        }
    }
    
    public String getAbsoluteFilePath(String fileName) {
    	logger.debug("Entering into getResourcePath " +fileName);
		Path filePath = fileStorageLocation.resolve(fileName).normalize();
        String path = filePath.toAbsolutePath().toString();
        logger.debug("Entering into getResourcePath path " +path);
        return path;

    }
    
}