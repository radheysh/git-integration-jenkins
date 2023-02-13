package com.fti.usdg.track.trace.models;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "shipment_details")
public class ShipmentDataEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public ShipmentDataEntity() {
	}
	
	private String shipper = null;
	private String dotClass = null;
	private String hazardWarning = null;
	private String stccCode = null;
	private String unNaNo = null;
	private String product = null;
	private String packingGroup = null;
	private String crudeClassification = null;
	private String shipDate = null;
	private String unitTrainNo = null;
	@Id
	private String bolNo = null;
	private String erpPiu = null;
	private String bswPerc = null;
	private String density = null;
	private String unitTrainSize = null;
	private String commodityName = null;
	private String eraflashD93b = null;
	
	@JsonIgnore
	private String inputFileId = null;
	private String createdDate = null;
 
	private String ledgerSyncFlag = null;
 
	private String updatedDate = null;
	private String ledgerUuid = null;
 
	private String status = null;
 
	private String updatedBy = null;
	
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	private String transactionHash = null;

	@JsonIgnore
	private String keyword = null;

	 
 

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTransactionHash() {
		return transactionHash;
	}

	public void setTransactionHash(String transactionHash) {
		this.transactionHash = transactionHash;
	}

	 

	/**
	 * @return the shipper
	 */
	public String getShipper() {
		return shipper;
	}

	/**
	 * @param shipper the shipper to set
	 */
	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	/**
	 * @return the dotClass
	 */
	public String getDotClass() {
		return dotClass;
	}

	/**
	 * @param dotClass the dotClass to set
	 */
	public void setDotClass(String dotClass) {
		this.dotClass = dotClass;
	}

	/**
	 * @return the hazardWarning
	 */
	public String getHazardWarning() {
		return hazardWarning;
	}

	/**
	 * @param hazardWarning the hazardWarning to set
	 */
	public void setHazardWarning(String hazardWarning) {
		this.hazardWarning = hazardWarning;
	}

	/**
	 * @return the stccCode
	 */
	public String getStccCode() {
		return stccCode;
	}

	/**
	 * @param stccCode the stccCode to set
	 */
	public void setStccCode(String stccCode) {
		this.stccCode = stccCode;
	}

	/**
	 * @return the unNaNo
	 */
	public String getUnNaNo() {
		return unNaNo;
	}

	/**
	 * @param unNaNo the unNaNo to set
	 */
	public void setUnNaNo(String unNaNo) {
		this.unNaNo = unNaNo;
	}

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the packingGroup
	 */
	public String getPackingGroup() {
		return packingGroup;
	}

	/**
	 * @param packingGroup the packingGroup to set
	 */
	public void setPackingGroup(String packingGroup) {
		this.packingGroup = packingGroup;
	}

	/**
	 * @return the crudeClassification
	 */
	public String getCrudeClassification() {
		return crudeClassification;
	}

	/**
	 * @param crudeClassification the crudeClassification to set
	 */
	public void setCrudeClassification(String crudeClassification) {
		this.crudeClassification = crudeClassification;
	}

	/**
	 * @return the shipDate
	 */
	public String getShipDate() {
		return shipDate;
	}

	/**
	 * @param shipDate the shipDate to set
	 */
	public void setShipDate(String shipDate) {
		this.shipDate = shipDate;
	}

	/**
	 * @return the unitTrainNo
	 */
	public String getUnitTrainNo() {
		return unitTrainNo;
	}

	/**
	 * @param unitTrainNo the unitTrainNo to set
	 */
	public void setUnitTrainNo(String unitTrainNo) {
		this.unitTrainNo = unitTrainNo;
	}

	/**
	 * @return the bolNo
	 */
	public String getBolNo() {
		return bolNo;
	}

	/**
	 * @param bolNo the bolNo to set
	 */
	public void setBolNo(String bolNo) {
		this.bolNo = bolNo;
	}

	/**
	 * @return the erpPiu
	 */
	public String getErpPiu() {
		return erpPiu;
	}

	/**
	 * @param erpPiu the erpPiu to set
	 */
	public void setErpPiu(String erpPiu) {
		this.erpPiu = erpPiu;
	}

	/**
	 * @return the bswPerc
	 */
	public String getBswPerc() {
		return bswPerc;
	}

	/**
	 * @param bswPerc the bswPerc to set
	 */
	public void setBswPerc(String bswPerc) {
		this.bswPerc = bswPerc;
	}

	/**
	 * @return the density
	 */
	public String getDensity() {
		return density;
	}

	/**
	 * @param density the density to set
	 */
	public void setDensity(String density) {
		this.density = density;
	}

	/**
	 * @return the unitTrainSize
	 */
	public String getUnitTrainSize() {
		return unitTrainSize;
	}

	/**
	 * @param unitTrainSize the unitTrainSize to set
	 */
	public void setUnitTrainSize(String unitTrainSize) {
		this.unitTrainSize = unitTrainSize;
	}

	/**
	 * @return the commodityName
	 */
	public String getCommodityName() {
		return commodityName;
	}

	/**
	 * @param commodityName the commodityName to set
	 */
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	/**
	 * @return the eraflashD93b
	 */
	public String getEraflashD93b() {
		return eraflashD93b;
	}

	/**
	 * @param eraflashD93b the eraflashD93b to set
	 */
	public void setEraflashD93b(String eraflashD93b) {
		this.eraflashD93b = eraflashD93b;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the ledgerSyncFlag
	 */
	public String getLedgerSyncFlag() {
		return ledgerSyncFlag;
	}

	/**
	 * @param ledgerSyncFlag the ledgerSyncFlag to set
	 */
	public void setLedgerSyncFlag(String ledgerSyncFlag) {
		this.ledgerSyncFlag = ledgerSyncFlag;
	}

	/**
	 * @return the ledgerUuid
	 */
	public String getLedgerUuid() {
		return ledgerUuid;
	}

	/**
	 * @param ledgerUuid the ledgerUuid to set
	 */
	public void setLedgerUuid(String ledgerUuid) {
		this.ledgerUuid = ledgerUuid;
	}

	/**
	 * @return the inputFileId
	 */
	public String getInputFileId() {
		return inputFileId;
	}

	/**
	 * @param inputFileId the inputFileId to set
	 */
	public void setInputFileId(String inputFileId) {
		this.inputFileId = inputFileId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}