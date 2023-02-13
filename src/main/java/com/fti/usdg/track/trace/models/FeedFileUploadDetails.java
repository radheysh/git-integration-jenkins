/**
 * 
 */
package com.fti.usdg.track.trace.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Anup
 *
 */

@Entity
@Table(name = "feedfile_upload_details")
public class FeedFileUploadDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String processingStatus = null;
	private Integer noOfRecord= null;
	private String updatedDate = null;
	private String ledgerSyncStatus = null;
	private String uploadedBy= null;
	private String inputFileId = null;
	private String inputFileName = null;
	private String errorDetails = null;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the processingStatus
	 */
	public String getProcessingStatus() {
		return processingStatus;
	}
	/**
	 * @param processingStatus the processingStatus to set
	 */
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	/**
	 * @return the noOfRecord
	 */
	public Integer getNoOfRecord() {
		return noOfRecord;
	}
	/**
	 * @param noOfRecord the noOfRecord to set
	 */
	public void setNoOfRecord(Integer noOfRecord) {
		this.noOfRecord = noOfRecord;
	}
	 
	/**
	 * @return the ledgerSyncStatus
	 */
	public String getLedgerSyncStatus() {
		return ledgerSyncStatus;
	}
	/**
	 * @param ledgerSyncStatus the ledgerSyncStatus to set
	 */
	public void setLedgerSyncStatus(String ledgerSyncStatus) {
		this.ledgerSyncStatus = ledgerSyncStatus;
	}
	/**
	 * @return the uploadedBy
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}
	/**
	 * @param uploadedBy the uploadedBy to set
	 */
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
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
	 * @return the inputFileName
	 */
	public String getInputFileName() {
		return inputFileName;
	}
	/**
	 * @param inputFileName the inputFileName to set
	 */
	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}
	/**
	 * @return the errorDetails
	 */
	public String getErrorDetails() {
		return errorDetails;
	}
	/**
	 * @param errorDetails the errorDetails to set
	 */
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
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
	 
	
}