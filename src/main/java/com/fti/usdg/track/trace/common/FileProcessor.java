/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fti.usdg.track.trace.controllers.FtiFileUploadController;
import com.fti.usdg.track.trace.models.*;
import com.fti.usdg.track.trace.repository.ShipmentDataRepository;
import com.fti.usdg.track.trace.service.BusinessService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.fti.usdg.track.trace.repository.FeedFileUploadRepository;

/**
 * @author AtharvaShri
 *
 */
@Service
public class FileProcessor {

	private static final Logger logger = LoggerFactory.getLogger(FtiFileUploadController.class);

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
			Integer noOfLinesToProcess, String userUUID) {
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

	public TrackTraceResponse processUploadedCsvFile(String absoluteFileName, Integer noOfLineToSkip,
			Integer noOfLinesToProcess, String userDetailsJwtToken)
			throws FileNotFoundException, IOException, CsvValidationException {
		TrackTraceResponse TrackTraceResponse = null;
		List<List<String>> records = new ArrayList<List<String>>();
		try (CSVReader csvReader = new CSVReader(new FileReader(absoluteFileName));) {
			String[] values = null;
			while ((values = csvReader.readNext()) != null) {
				records.add(Arrays.asList(values));
			}
		}
		List<ShipmentDataEntity> _list = new ArrayList<ShipmentDataEntity>();
		Boolean processingFailed = false;
		if (records != null && records.size() > 0) {
			ShipmentDataEntity ShipmentDataEntity = null;
			Boolean isHeader = true;
			for (List<String> list : records) {
				if (isHeader) {
					if (!validateHeader(list)) {
						TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
								" There are error while file processing. Valid File header should conatins ( Shipper	Ship Date	Unit Train No	DOT Class	Hazard Warning	BOL NO.	E.R.P./P.I.U.	BSW %	Density	Unit Train Size	STCC Code	UN NA No.	Product	Packing Group	Commodity Name	Eraflash D93B	Crude Classification ");
						processingFailed = true;
						break;
						
					}
					isHeader = false;
				} else {
					ShipmentDataEntity = populateShipmentDataEntity(list);
					if(ShipmentDataEntity!=null) {
						_list.add(ShipmentDataEntity);
					}
				}
			}
			if(!processingFailed) {
				FeedFileUploadDetails ffud = new FeedFileUploadDetails();
				try {
					if (_list != null && _list.size() > 0) {
						ffud.setInputFileId(_list.get(0).getInputFileId());
						ffud.setNoOfRecord(_list.size());
						if(_list.size()>0) {
							ShipmentDataRepository.saveAll(_list);
						}
						TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
								"File proccess successfully. "+_list.size()+" rows has been saved successfully.");
					} else {
						TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_200,
								"File proccess successfully. "+_list.size()+" rows has been saved successfully.");
						ffud.setNoOfRecord(_list.size());
					}
					ffud.setProcessingStatus(Constants.PROCCESSED);
					ffud.setUpdatedDate(UtilityMethods.getCurrentDate());
					ffud.setUploadedBy(Constants.ADMIN);
					ffud.setLedgerSyncStatus(Constants.No);
					String fileName = absoluteFileName.substring(absoluteFileName.lastIndexOf(File.separator) + 1);
					ffud.setInputFileName(fileName);
					FeedFileUploadRepository.save(ffud);
				} catch (Exception e) {
					e.printStackTrace();
					TrackTraceResponse = new TrackTraceResponse(Constants.RESPONSE_CODE_500,
							" There are error while file processing. Valid File header should conatins ( Shipper	Ship Date	Unit Train No	DOT Class	Hazard Warning	BOL NO.	E.R.P./P.I.U.	BSW %	Density	Unit Train Size	STCC Code	UN NA No.	Product	Packing Group	Commodity Name	Eraflash D93B	Crude Classification ");
					UtilityMethods.handleError(absoluteFileName, Constants.FILE_HEADER_INVALID);
				}
			}
		}
		return TrackTraceResponse;
	}

	private Boolean validateHeader(List<String> list) {
		Boolean isMatchingHeader = true;
		if (list.size() != 17) {
			return false;
		}
		if (list.get(0)==null || !list.get(0).trim().equalsIgnoreCase(Constants.SHIPPER)) {
			isMatchingHeader = false;
		}
		if (list.get(1)==null || !list.get(1).trim().equalsIgnoreCase(Constants.SHIP_DATE)) {
			isMatchingHeader = false;
		}
		if (list.get(2)==null || !list.get(2).trim().equalsIgnoreCase(Constants.UNIT_TRAIN_NO)) {
			isMatchingHeader = false;
		}
		if (list.get(3)==null || !list.get(3).trim().equalsIgnoreCase(Constants.DOT_CLASS)) {
			isMatchingHeader = false;
		}
		if (list.get(4)==null || !list.get(4).trim().equalsIgnoreCase(Constants.HAZARD_WARNING)) {
			isMatchingHeader = false;
		}
		if (list.get(5)==null || !list.get(5).trim().equalsIgnoreCase(Constants.BOL_NO)) {
			isMatchingHeader = false;
		}
		if (list.get(6)==null || !list.get(6).trim().equalsIgnoreCase(Constants.ERP_PIU)) {
			isMatchingHeader = false;
		}
		if (list.get(7)==null || !list.get(7).trim().equalsIgnoreCase(Constants.BSW)) {
			isMatchingHeader = false;
		}
		if (list.get(8)==null || !list.get(8).trim().equalsIgnoreCase(Constants.DENSITY)) {
			isMatchingHeader = false;
		}
		if (list.get(9)==null || !list.get(9).trim().equalsIgnoreCase(Constants.UNIT_TRAIN_SIZE)) {
			isMatchingHeader = false;
		}
		if (list.get(10)==null || !list.get(10).trim().equalsIgnoreCase(Constants.STCC_CODE)) {
			isMatchingHeader = false;
		}
		if (list.get(11)==null || !list.get(11).trim().equalsIgnoreCase(Constants.UN_NA_NO)) {
			isMatchingHeader = false;
		}
		if (list.get(12)==null || !list.get(12).trim().equalsIgnoreCase(Constants.PRODUCT)) {
			isMatchingHeader = false;
		}
		if (list.get(13)==null || !list.get(13).trim().equalsIgnoreCase(Constants.PACKING_GROUP)) {
			isMatchingHeader = false;
		}
		if (list.get(14)==null || !list.get(14).trim().equalsIgnoreCase(Constants.COMMODITY_NAME)) {
			isMatchingHeader = false;
		}
		if (list.get(15)==null || !list.get(15).trim().equalsIgnoreCase(Constants.ERAFLASH_D93B)) {
			isMatchingHeader = false;
		}
		if (list.get(16)==null || !list.get(16).trim().equalsIgnoreCase(Constants.CRUDE_TYPE)) {
			isMatchingHeader = false;
		}
		return isMatchingHeader;
	}

	private ShipmentDataEntity populateShipmentDataEntity(List<String> dataList) {
		ShipmentDataEntity selectedRow = null;
		if (dataList.size() != 17 || dataList.get(5)==null  || dataList.get(5).trim().length()<1) {
			return selectedRow;
		} else {
			selectedRow = new ShipmentDataEntity();
			logger.debug(dataList.get(5).trim() +" "+dataList.get(1).trim());
			selectedRow.setShipper(dataList.get(0).trim());
			selectedRow.setShipDate(UtilityMethods.convertShipDateToYYYMMDD(dataList.get(1).trim()));
			selectedRow.setUnitTrainNo(dataList.get(2).trim());
			selectedRow.setDotClass(dataList.get(3).trim());
			selectedRow.setHazardWarning(dataList.get(4).trim());
			selectedRow.setBolNo(dataList.get(5).trim());
			selectedRow.setErpPiu(dataList.get(6).trim());
			selectedRow.setBswPerc(dataList.get(7).trim());
			selectedRow.setDensity(dataList.get(8).trim());
			selectedRow.setUnitTrainSize(dataList.get(9).trim());
			selectedRow.setStccCode(dataList.get(10).trim());
			selectedRow.setUnNaNo(dataList.get(11).trim());
			selectedRow.setProduct(dataList.get(12).trim());
			selectedRow.setPackingGroup(dataList.get(13).trim());
			selectedRow.setCommodityName(dataList.get(14).trim());
			selectedRow.setEraflashD93b(dataList.get(15).trim());
			selectedRow.setCrudeClassification(dataList.get(16).trim());
			selectedRow.setStatus(Constants.ACTIVE);
			selectedRow.setLedgerSyncFlag(Constants.No);
			selectedRow.setCreatedDate(UtilityMethods.getCurrentDate());
			selectedRow.setUpdatedDate(UtilityMethods.getCurrentDate());
			selectedRow.setLedgerUuid(String.valueOf(UUID.randomUUID()));
			selectedRow.setKeyword(concatRowData(selectedRow));

			if(!checkForNoDataChange(selectedRow)) {
				selectedRow = null;
			}
		}
		return selectedRow;
	}

	private Boolean checkForNoDataChange(ShipmentDataEntity sde) {
		Boolean dirty = true;
		Integer dataHash = (Integer)AppCacheUtils.getValue(Constants.SHIPMENT_DATA_BOL_NO+sde.getBolNo());
		if(dataHash!=null && dataHash.intValue()==concatRowData(sde).hashCode()) {
			dirty = false;
		}else {
			AppCacheUtils.putValue(Constants.SHIPMENT_DATA_BOL_NO+sde.getBolNo(), concatRowData(sde).hashCode());
		}
		return dirty;
	}

	private String concatRowData(ShipmentDataEntity shipmentData) {

		StringBuilder keyword = new StringBuilder();
		keyword.append(shipmentData.getShipper()).append(Constants.SPACE);
		keyword.append(shipmentData.getDotClass()).append(Constants.SPACE);
		keyword.append(shipmentData.getHazardWarning()).append(Constants.SPACE);
		keyword.append(shipmentData.getStccCode()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnNaNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getProduct()).append(Constants.SPACE);
		keyword.append(shipmentData.getPackingGroup()).append(Constants.SPACE);
		keyword.append(shipmentData.getCrudeClassification()).append(Constants.SPACE);
		keyword.append(shipmentData.getShipDate()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnitTrainNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getBolNo()).append(Constants.SPACE);
		keyword.append(shipmentData.getErpPiu()).append(Constants.SPACE);
		keyword.append(shipmentData.getBswPerc()).append(Constants.SPACE);
		keyword.append(shipmentData.getDensity()).append(Constants.SPACE);
		keyword.append(shipmentData.getUnitTrainSize()).append(Constants.SPACE);
		keyword.append(shipmentData.getCommodityName()).append(Constants.SPACE);
		keyword.append(shipmentData.getEraflashD93b()).append(Constants.SPACE);
		return keyword.toString();

	}
}
