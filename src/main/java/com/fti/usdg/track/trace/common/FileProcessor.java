/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fti.usdg.track.trace.models.*;
import com.fti.usdg.track.trace.repository.ShipmentDataRepository;
import com.fti.usdg.track.trace.service.BusinessService;
import com.fti.usdg.track.trace.repository.FeedFileUploadRepository;

/**
 * @author Anup
 *
 */
@Service
public class FileProcessor {

	@Autowired
	private ExcelProcessor ExcelProcessor = null;
	@Autowired
	private ShipmentDataRepository ShipmentDataRepository = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;
	@Autowired
	private FeedFileUploadRepository FeedFileUploadRepository = null;
	@Autowired
	BusinessService BusinessService = null;

	public TrackTraceResponse processUploadedXLSxFile(String absoluteFilePath, Integer noOfLineToSkip,
			Integer noOfLinesToProcess,String userUUID) {
		TrackTraceResponse ttr = null;
		FeedFileUploadDetails ffud = new FeedFileUploadDetails();
		List<ShipmentDataEntity> _list;
		try {
			_list = ExcelProcessor.parseExcel(absoluteFilePath, 0, 5000, 1);
			if (_list != null && _list.size() > 0) {
				ffud.setInputFileId(_list.get(0).getInputFileId());
				ffud.setNoOfRecord(_list.size());
				ShipmentDataRepository.saveAll(_list);
				ttr = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
						"File proccess successfully. " + _list.size() + " No of lines saved ");
			} else {
				ttr = new TrackTraceResponse(Constants.RESPONSE_CODE_500, " There are error while file processing ");
			}
			ffud.setProcessingStatus(Constants.PROCCESSED);
			ffud.setUpdatedDate(UtilityMethods.getCurrentDate());
			ffud.setUploadedBy(Constants.ADMIN);
			ffud.setLedgerSyncStatus(Constants.No);
			String fileName = absoluteFilePath.substring(absoluteFilePath.lastIndexOf(File.separator) + 1);
			ffud.setInputFileName(fileName);
			FeedFileUploadRepository.save(ffud);
		} catch (Exception e) {
			e.printStackTrace();
			ttr = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
					" There are error while file processing. Valid File header should conatins ( Shipper	Ship Date	Unit Train No	DOT Class	Hazard Warning	BOL NO.	E.R.P./P.I.U.	BSW %	Density	Unit Train Size	STCC Code	UN NA No.	Product	Packing Group	Commodity Name	Eraflash D93B	Crude Classification ");
			UtilityMethods.handleError(absoluteFilePath, Constants.FILE_HEADER_INVALID);
		}
		return ttr;
	}

}
