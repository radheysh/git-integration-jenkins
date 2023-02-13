/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class DeleteShipmentReq {
	
	private String bolNumber  = null;
	private String ledgerUuid = null;
	
	 
	 
	/**
	 * @return the bolNumber
	 */
	public String getBolNumber() {
		return bolNumber;
	}
	/**
	 * @param bolNumber the bolNumber to set
	 */
	public void setBolNumber(String bolNumber) {
		this.bolNumber = bolNumber;
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
	
	
	
			

}
