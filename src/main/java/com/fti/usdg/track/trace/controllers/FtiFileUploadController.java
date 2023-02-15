/**
 * 
 */
package com.fti.usdg.track.trace.controllers;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fti.usdg.track.trace.common.FileStorageService;
import com.fti.usdg.track.trace.common.TrackTraceResponse;
import com.fti.usdg.track.trace.common.UtilityMethods;
import com.fti.usdg.track.trace.security.jwt.JwtUtils;
import com.fti.usdg.track.trace.common.Constants;
import com.fti.usdg.track.trace.common.FileProcessor;

/**
 * @author Anup
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/webapp/")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class FtiFileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FtiFileUploadController.class);

	@Autowired
	private FileStorageService fileStorageService = null;
	@Autowired
	private FileProcessor FileProcessor = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;
	@Autowired
	private JwtUtils jwtUtils = null;

	@PostMapping("/upload")
	public ResponseEntity<TrackTraceResponse> uploadFile(@RequestParam("file") MultipartFile file,
			HttpServletRequest httpRequest) {
		logger.debug("Entering into uploadFile ");
		String fileName = fileStorageService.storeFile(file);
		logger.debug("Entering into uploadFile	 File Name " + fileName);
		Integer noOfLineToSkip = 1;
		Integer noOfLinesToProcess = 50000;
		String absoluteFileName = fileStorageService.getAbsoluteFilePath(fileName);
		TrackTraceResponse TrackTraceResponse = null;
		try {
			if (fileName.endsWith(".csv")) {
				TrackTraceResponse = FileProcessor.processUploadedCsvFile(absoluteFileName, noOfLineToSkip,
						noOfLinesToProcess,jwtUtils.getUserDetailsJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
			}else if (fileName.endsWith(".xlsx")) {
				TrackTraceResponse = FileProcessor.processUploadedXLSxFile(absoluteFileName, noOfLineToSkip,
						noOfLinesToProcess,jwtUtils.getUserDetailsJwtToken(getHeaderKey(httpRequest, Constants.JWT_TOKEN_KEY)));
			} else {
				UtilityMethods.handleError(absoluteFileName, Constants.IN_CORRECT_FORMAT);
				TrackTraceResponse = new TrackTraceResponse("500", "File Format not supported, only .xlsx supported");
			}
		} catch (Exception ex) {
			UtilityMethods.handleError(absoluteFileName, Constants.FILE_HEADER_INVALID);
			TrackTraceResponse = UtilityMethods.handleException(ex);
		}
		logger.debug("Leaving from uploadFile UploadFileResponse " + TrackTraceResponse);
		return getResponseObject(TrackTraceResponse);
	}

	private ResponseEntity<TrackTraceResponse> getResponseObject(TrackTraceResponse TrackTraceResponse) {
		ResponseEntity<TrackTraceResponse> ResponseEntity = null;
		if (TrackTraceResponse != null
				&& TrackTraceResponse.getResponseCode().equalsIgnoreCase(Constants.RESPONSE_CODE_200)) {
			ResponseEntity = new ResponseEntity<>(TrackTraceResponse, HttpStatus.OK);
		} else {
			ResponseEntity = new ResponseEntity<>(TrackTraceResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.debug("info from getResponseObject " + TrackTraceResponse);
		return ResponseEntity;
	}

	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		logger.debug("Entering into downloadFile ");
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		logger.debug("Leaving from downloadFile downloadFile ");
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);

	}

	@SuppressWarnings("rawtypes")
	private String getHeaderKey(HttpServletRequest httpRequest, String key) {
		Enumeration values = httpRequest.getHeaders(key);
		String value = null;
		if (values != null) {
			while (values.hasMoreElements()) {
				value = (String) values.nextElement();
			}
		}
		return value;
	}
}
