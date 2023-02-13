/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class BatteryDetails {

	
	private String manufacturerName = null;
	private String batteryType = null;
	private String batteryID = null;
	private String manufacturerDigitalSignature = null;
	
	private String ledgerRecordId = null;
	/**
	 * @return the ledgerRecordId
	 */
	public String getLedgerRecordId() {
		return ledgerRecordId;
	}
	/**
	 * @param ledgerRecordId the ledgerRecordId to set
	 */
	public void setLedgerRecordId(String ledgerRecordId) {
		this.ledgerRecordId = ledgerRecordId;
	}
	/**
	 * @return the manufacturerName
	 */
	public String getManufacturerName() {
		return manufacturerName;
	}
	/**
	 * @param manufacturerName the manufacturerName to set
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	/**
	 * @return the batteryType
	 */
	public String getBatteryType() {
		return batteryType;
	}
	/**
	 * @param batteryType the batteryType to set
	 */
	public void setBatteryType(String batteryType) {
		this.batteryType = batteryType;
	}
	/**
	 * @return the batteryID
	 */
	public String getBatteryID() {
		return batteryID;
	}
	/**
	 * @param batteryID the batteryID to set
	 */
	public void setBatteryID(String batteryID) {
		this.batteryID = batteryID;
	}
	/**
	 * @return the manufacturerDigitalSignature
	 */
	public String getManufacturerDigitalSignature() {
		return manufacturerDigitalSignature;
	}
	/**
	 * @param manufacturerDigitalSignature the manufacturerDigitalSignature to set
	 */
	public void setManufacturerDigitalSignature(String manufacturerDigitalSignature) {
		this.manufacturerDigitalSignature = manufacturerDigitalSignature;
	}
	
	 
}
