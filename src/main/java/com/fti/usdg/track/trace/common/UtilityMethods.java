/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fti.usdg.track.trace.models.FeedFileUploadDetails;

import com.fti.usdg.track.trace.repository.FeedFileUploadRepository;

/**
 * @author Anup
 *
 */
@Service
public class UtilityMethods {

	@Autowired
	FeedFileUploadRepository FeedFileUploadRepository = null;

	public String parseJWTToken(String headerKey) {
		// TODO Auto-generated method stub
		return null;
	}

	public TrackTraceResponse handleException(Exception ex) {
		ex.printStackTrace();
		return new TrackTraceResponse(Constants.RESPONSE_CODE_500,
				"There are error while processing of request. Please contact admin with this uuid "
						+ UUID.randomUUID().toString() + " " + ex.getMessage());
	}

	public String getCurrentDate() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = new Date();
		return sdfDate.format(date);
	}

	public String getCurrentDate4FileName() {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Date date = new Date();
		return sdfDate.format(date);
	}

	// 06-Jan-2021
	public String convertExcelDateToIso(String inputDate) {
		String formattedDate = null;
		try {
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(inputDate);
			formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
			System.out.println(formattedDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formattedDate;

	}

	public static Boolean isFirstDateIsBefore(String firstDate, String secondDate) throws ParseException {
		if (convertIsoDateStringToDate(firstDate).before(convertIsoDateStringToDate(secondDate))) {
			return true;
		} else {
			return false;
		}
	}

	public static Date convertIsoDateStringToDate(String stringDate) throws ParseException {
		SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		Date date = null;
		date = sdfo.parse(stringDate);
		return date;
	}

	public static void main(String aa[]) {
		UtilityMethods UtilityMethods = new UtilityMethods();
		System.out.println(UtilityMethods.convertShipDateToYYYMMDD("1/5/2022"));
		System.out.println(UtilityMethods.convertMMDDYYYYFromIso("2021-12-01T00:00:00.000Z"));
		
	}

	public String convertShipDateToYYYMMDD(String inputDate) {
		/*
		 * String formattedDate = null; try { Date date = new
		 * SimpleDateFormat("mm/dd/yyyy").parse(inputDate); formattedDate = new
		 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date); } catch
		 * (ParseException e) { e.printStackTrace(); } return formattedDate;
		 */
		StringBuilder sb = new StringBuilder();
		sb.append(inputDate.substring(inputDate.lastIndexOf("/") + 1) + "-");
		sb.append(inputDate.substring(0, inputDate.indexOf("/")) + "-");
		sb.append(inputDate.substring(inputDate.indexOf("/") + 1, inputDate.lastIndexOf("/")) + Constants.START_TS);

		return sb.toString();
	}

	public String convertMMDDYYYY(String inputDate) {
		String formattedDate = null;
		Date date = null;
		try {
			if (inputDate.contains("T")) {
				date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(inputDate);
			} else {
				date = new SimpleDateFormat("yyyy-MM-dd").parse(inputDate);
			}
			formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	public String convertMMDDYYYYFromIso(String inputDate) {
		String formattedDate = null;
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(inputDate);
			formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formattedDate;
	}

	public void handleError(String absoluteFilePath, String errorType) {
		FeedFileUploadDetails ffud = new FeedFileUploadDetails();
		ffud.setUpdatedDate(getCurrentDate());
		ffud.setUploadedBy(Constants.ADMIN);
		ffud.setLedgerSyncStatus(Constants.No);
		String fileName = absoluteFilePath.substring(absoluteFilePath.lastIndexOf(File.separator) + 1);
		ffud.setInputFileName(fileName);
		ffud.setProcessingStatus(Constants.ERRONEOUS);
		if (errorType.equalsIgnoreCase(Constants.FILE_HEADER_INVALID)) {
			ffud.setErrorDetails("Invalid File Headers. Please check reference.xlsx.");
		} else if (errorType.equalsIgnoreCase(Constants.IN_CORRECT_FORMAT)) {
			ffud.setErrorDetails("Invalid Extensions. The file type is not supported.");
		}
		FeedFileUploadRepository.save(ffud);
		File rejectedFilePath = new File(
				Constants.USER_HOME + File.separator + Constants.FEED_FILES + File.separator + Constants.REJECTED);
		if (!rejectedFilePath.exists()) {
			rejectedFilePath.mkdirs();
		}
		new File(absoluteFilePath).renameTo(new File(
				rejectedFilePath.getAbsoluteFile() + File.separator + UUID.randomUUID().toString() + "_" + fileName));

	}
}
