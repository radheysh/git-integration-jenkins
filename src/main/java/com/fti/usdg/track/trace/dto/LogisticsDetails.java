/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class LogisticsDetails {

	 
	private String logisticsFirmName = null;
	private String receivershipdate = null;
	private String driverName = null;
	private String driverDigitalSignature = null;
	 
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
	 * @return the logisticsFirmName
	 */
	public String getLogisticsFirmName() {
		return logisticsFirmName;
	}
	/**
	 * @param logisticsFirmName the logisticsFirmName to set
	 */
	public void setLogisticsFirmName(String logisticsFirmName) {
		this.logisticsFirmName = logisticsFirmName;
	}
	/**
	 * @return the receivershipdate
	 */
	public String getReceivershipdate() {
		return receivershipdate;
	}
	/**
	 * @param receivershipdate the receivershipdate to set
	 */
	public void setReceivershipdate(String receivershipdate) {
		this.receivershipdate = receivershipdate;
	}
	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}
	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	/**
	 * @return the driverDigitalSignature
	 */
	public String getDriverDigitalSignature() {
		return driverDigitalSignature;
	}
	/**
	 * @param driverDigitalSignature the driverDigitalSignature to set
	 */
	public void setDriverDigitalSignature(String driverDigitalSignature) {
		this.driverDigitalSignature = driverDigitalSignature;
	}
	
	
	
}
