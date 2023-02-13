/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class QaDetails {

	private String qaCompanyName = null;
	private String qaEngineerName = null;
	private Boolean pass = null;
	private String qaEngineerDigitalSignature = null;

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
	 * @return the qaCompanyName
	 */
	public String getQaCompanyName() {
		return qaCompanyName;
	}

	/**
	 * @param qaCompanyName the qaCompanyName to set
	 */
	public void setQaCompanyName(String qaCompanyName) {
		this.qaCompanyName = qaCompanyName;
	}

	/**
	 * @return the qaEngineerName
	 */
	public String getQaEngineerName() {
		return qaEngineerName;
	}

	/**
	 * @param qaEngineerName the qaEngineerName to set
	 */
	public void setQaEngineerName(String qaEngineerName) {
		this.qaEngineerName = qaEngineerName;
	}

	/**
	 * @return the pass
	 */
	public Boolean getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(Boolean pass) {
		this.pass = pass;
	}

	/**
	 * @return the qaEngineerDigitalSignature
	 */
	public String getQaEngineerDigitalSignature() {
		return qaEngineerDigitalSignature;
	}

	/**
	 * @param qaEngineerDigitalSignature the qaEngineerDigitalSignature to set
	 */
	public void setQaEngineerDigitalSignature(String qaEngineerDigitalSignature) {
		this.qaEngineerDigitalSignature = qaEngineerDigitalSignature;
	}

}
