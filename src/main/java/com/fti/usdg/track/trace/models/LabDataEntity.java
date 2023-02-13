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
 * @author bgadm
 *
 */

@Entity
@Table(name = "lab_data")
public class LabDataEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String customer = null;
	private String product = null;
	private String testDate = null;
	private String dotClass = null;
	private String hazardWarning = null;
	private String bswPercentage  = null;
	private String trasactionHash  = null;
	private String blockNo  = null;
	private String createdBy  = null;
	private String createdAt  = null;
	private String updatedBy  = null;
	private String updatedAt  = null;
	private String status  = null;
	
	
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
	/**
	 * @return the blockNo
	 */
	public String getBlockNo() {
		return blockNo;
	}
	/**
	 * @param blockNo the blockNo to set
	 */
	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}
	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
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
	 * @return the updatedAt
	 */
	public String getUpdatedAt() {
		return updatedAt;
	}
	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
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
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}
	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
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
	 * @return the testDate
	 */
	public String getTestDate() {
		return testDate;
	}
	/**
	 * @param testDate the testDate to set
	 */
	public void setTestDate(String testDate) {
		this.testDate = testDate;
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
	 * @return the bswPercentage
	 */
	public String getBswPercentage() {
		return bswPercentage;
	}
	/**
	 * @param bswPercentage the bswPercentage to set
	 */
	public void setBswPercentage(String bswPercentage) {
		this.bswPercentage = bswPercentage;
	}
	/**
	 * @return the trasactionHash
	 */
	public String getTrasactionHash() {
		return trasactionHash;
	}
	/**
	 * @param trasactionHash the trasactionHash to set
	 */
	public void setTrasactionHash(String trasactionHash) {
		this.trasactionHash = trasactionHash;
	}
	
}
