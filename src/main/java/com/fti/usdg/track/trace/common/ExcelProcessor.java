/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fti.usdg.track.trace.security.services.BusinessValidation;
import com.fti.usdg.track.trace.dto.ShipmentDataDto;
import com.fti.usdg.track.trace.models.*;
import com.fti.usdg.track.trace.repository.LabDataRepository;

 

/**
 * @author Anup
 *
 */
@Service
public class ExcelProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ExcelProcessor.class);

	@Autowired
	private LabDataRepository LabDataRepository = null;
	@Autowired
	private  BusinessValidation BusinessValidation = null;
	@Autowired
	private UtilityMethods UtilityMethods = null;

	public List<ShipmentDataEntity> parseExcel(String excelFilePath, Integer sheetNo, Integer lineCount,
			Integer noOfLineToSkip) throws Exception {
		logger.debug("Processing Started " + excelFilePath + "sheetNo " + sheetNo + "lineCount " + lineCount
				+ "noOfLineToSkip " + noOfLineToSkip);
		List<ShipmentDataEntity> ShipperExcelDtoList = new ArrayList<>();
		Map<String,String> errorMap = new HashMap<String,String>();
		Map<Integer, String> headers = new LinkedHashMap<>();
		FileInputStream file;
		ShipmentDataEntity ShipperExcelDto = null;
		file = new FileInputStream(new File(excelFilePath));
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(sheetNo);
		Iterator<Row> rowIterator = sheet.iterator();
		Iterator<Row> rowIteratorForHeaders = sheet.iterator();
		XSSFFormulaEvaluator.evaluateAllFormulaCells(workbook);
		workbook.setForceFormulaRecalculation(true);
		logger.debug(" Parsing done ");
		String fileName = null;
		fileName = excelFilePath.substring(excelFilePath.lastIndexOf(File.separator)+1);
		fileName = fileName +"_"+UtilityMethods.getCurrentDate4FileName();
		for (Integer index = 0; index < noOfLineToSkip; index++) {
			rowIterator.next();
			headers = getHeaders(rowIteratorForHeaders);
			rowIteratorForHeaders.next();
		}
		Integer rowCount = 0;
		Integer noOfBlankLines = 1;
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			rowCount++;
			if (rowCount.intValue() == 5000) {
				break;
			}
			List<Cell> cells = new ArrayList<>();
			int lastColumn = Math.max(row.getLastCellNum(), 28);
			for (int cn = 0; cn < lastColumn; cn++) {
				Cell c = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
				cells.add(c);
			}
			Boolean nullsOnly = cells.stream().noneMatch(Objects::nonNull);
			if (nullsOnly) {
				noOfBlankLines++;
			} else {
				noOfBlankLines = 1;
				ShipperExcelDto = extractInfoFromCell(cells, headers,errorMap);
				if (ShipperExcelDto != null) {
					ShipperExcelDto.setInputFileId(fileName);
					ShipperExcelDtoList.add(ShipperExcelDto);
				}else {
					workbook.close();
					file.close();
					throw new Exception ("File headers is not corrcet. Valid File header should conatins ( Shipper	Ship Date	Unit Train No	DOT Class	Hazard Warning	BOL NO.	E.R.P./P.I.U.	BSW %	Density	Unit Train Size	STCC Code	UN NA No.	Product	Packing Group	Commodity Name	Eraflash D93B	Crude Classification"); 
				}
			}
			if (noOfBlankLines > Constants.NO_OF_BLANK_LINES) {
				break;
			}

		}
		workbook.close();
		file.close();
		return ShipperExcelDtoList;
	}

	private ShipmentDataEntity extractInfoFromCell(List<Cell> cells, Map<Integer, String> headers,Map<String,String> errorMap) {
		DataFormatter formatter = new DataFormatter();
		ShipmentDataEntity selectedRow = null;
		String shipDate = null;
		if(headers.size()!=17) {
			return selectedRow;
		}else {
			selectedRow = new ShipmentDataEntity();
			for (int i = 0; i < headers.size(); i++) {
				Cell cell = cells.get(i);
				String s = headers.get(i);
				switch (s) {
				case Constants.SHIPPER:
					if (cell != null) {
						selectedRow.setShipper(getCellValue(cell));
					}
					break;
				case Constants.SHIP_DATE:
					if (cell != null) {
						shipDate = UtilityMethods.convertExcelDateToIso(getCellValue(cell));
						selectedRow.setShipDate(shipDate);
					}
					break;
				case Constants.UNIT_TRAIN_NO:
					if (cell != null) {
						selectedRow.setUnitTrainNo(getCellValue(cell));
					}
					break;
				case Constants.DOT_CLASS:
					if (cell != null) {
						selectedRow.setDotClass(getCellValue(cell));
					}
					break;
				case Constants.HAZARD_WARNING:
					if (cell != null) {
						selectedRow.setHazardWarning(getCellValue(cell));
					}
					break;
				case Constants.BOL_NO:
					if (cell != null) {
						selectedRow.setBolNo(getCellValue(cell));
					}
					break;
				case Constants.ERP_PIU:
					if (cell != null) {
						selectedRow.setErpPiu(getCellValue(cell));
					}
					break;
				case Constants.BSW:
					if (cell != null) {
						selectedRow.setBswPerc(getCellValue(cell));
					}
					break;

				case Constants.DENSITY:
					if (cell != null) {
						selectedRow.setDensity(getCellValue(cell));
					}
					break;
				case Constants.UNIT_TRAIN_SIZE:
					if (cell != null) {
						selectedRow.setUnitTrainSize(getCellValue(cell));
					}
					break;
				case Constants.STCC_CODE:
					if (cell != null) {
						selectedRow.setStccCode(getCellValue(cell));
					}
					break;
				case Constants.UN_NA_NO:
					if (cell != null) {
						selectedRow.setUnNaNo(getCellValue(cell));
					}
					break;
				case Constants.PRODUCT:
					if (cell != null) {
						selectedRow.setProduct(getCellValue(cell));
					}
					break;
				case Constants.PACKING_GROUP:
					if (cell != null) {
						selectedRow.setPackingGroup(formatter.formatCellValue(cell));
					}
					break;
				case Constants.COMMODITY_NAME:
					if (cell != null) {
						selectedRow.setCommodityName(formatter.formatCellValue(cell));
					}
					break;
				case Constants.ERAFLASH_D93B:
					if (cell != null) {
						selectedRow.setEraflashD93b(getCellValue(cell));
					}
					break;
				case Constants.CRUDE_TYPE:
					if (cell != null) {
						selectedRow.setCrudeClassification(getCellValue(cell));
					}
					break;
				default:
					logger.error(s + " not supported in file header. Valid File header should conatins ( Shipper	Ship Date	Unit Train No	DOT Class	Hazard Warning	BOL NO.	E.R.P./P.I.U.	BSW %	Density	Unit Train Size	STCC Code	UN NA No.	Product	Packing Group	Commodity Name	Eraflash D93B	Crude Classification");
					selectedRow = null;
					break;
				}
				selectedRow.setStatus(Constants.ACTIVE);
				selectedRow.setLedgerSyncFlag(Constants.No);
				selectedRow.setCreatedDate(UtilityMethods.getCurrentDate());
				selectedRow.setUpdatedDate(UtilityMethods.getCurrentDate());
				selectedRow.setLedgerUuid(String.valueOf(UUID.randomUUID()));
				selectedRow.setKeyword(getKeywordSearchText(selectedRow));
			}
		}
		LabDataEntity ldFromDb = LabDataRepository.findByCustomerAndProductAndStatus(selectedRow.getShipper(),
				selectedRow.getProduct(), Constants.ACTIVE);
		if (ldFromDb != null) {
			selectedRow.setDotClass(ldFromDb.getDotClass());
			selectedRow.setHazardWarning(ldFromDb.getHazardWarning());
			selectedRow.setBswPerc(ldFromDb.getBswPercentage());
		}
		String errorMessage = BusinessValidation.validateData(selectedRow);
		if(errorMessage!=null && errorMessage.length()>0) {
			errorMap.put(selectedRow.getBolNo(),BusinessValidation.validateData(selectedRow));
			selectedRow.setStatus(Constants.CAP_REJECTED);
		}
		logger.debug(selectedRow.toString());
		return selectedRow;
	}

	private String getKeywordSearchText(ShipmentDataEntity shipmentData) {

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
	
	private String getCellValue(Cell cell) {
		DataFormatter formatter = new DataFormatter();
		String cellValue = "";
		if (cell.getCellType() == CellType.NUMERIC) {
			if (DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				DateFormat targetFormat = new SimpleDateFormat(Constants.DATE_FORMAT_1);
				cellValue = targetFormat.format(date);
			} else {
				cellValue = formatter.formatCellValue(cell);
			}
		} else if (cell.getCellType() == CellType.STRING) {
			cellValue = cell.getStringCellValue();
		} else if (cell.getCellType() == CellType.BOOLEAN) {
			cellValue = String.valueOf(cell.getBooleanCellValue());
		}
		return cellValue;
	}

	public Map<Integer, String> getHeaders(Iterator<Row> rowIterator) {
		Map<Integer, String> headers = new LinkedHashMap<>();
		Row row = rowIterator.next();
		for (Cell cell : row) {
			headers.put(cell.getColumnIndex(), cell.getStringCellValue());
		}
		return headers;
	}
}
