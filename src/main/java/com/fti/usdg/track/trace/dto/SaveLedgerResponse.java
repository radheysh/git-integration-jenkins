/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class SaveLedgerResponse {
	
	private String transactionId = null;
	private Boolean successful = null;
	private String blockNumber = null;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Boolean getSuccessful() {
		return successful;
	}

	public void setSuccessful(Boolean successful) {
		this.successful = successful;
	}

	public String getBlockNumber() {
		return blockNumber;
	}

	public void setBlockNumber(String blockNumber) {
		this.blockNumber = blockNumber;
	}

}
