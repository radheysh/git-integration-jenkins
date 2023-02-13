/**
 * 
 */
package com.fti.usdg.track.trace.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Anup
 *
 */

@Entity
@Table(name = "user_group")
public class UserGroup {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String groupName = null;
	private String groupDescription = null;
	private String featureIds= null;
	private Integer noOfUserAttached= null;
	@Transient
    @Formula("(SELECT ud.username FROM users ud WHERE ud.uuid = created_by_value)")
	private String createdBy= null;	
	private String createdAt= null;	
	@Transient
    @Formula("(SELECT ud.username FROM users ud WHERE ud.uuid = updated_by_value)")
	private String updatedBy= null;	
	private String updatedAt= null;
	private String status= null;	
	private String shipper= null;	
	

	private String createdByValue= null;	
	@JsonIgnore
	private String updatedByValue= null;	
	
	
	private String shipmentDateBefore= null;	
	private String shipmentDateAfter= null;	
	
	
	
	/**
	 * @return the shipmentDateBefore
	 */
	public String getShipmentDateBefore() {
		return shipmentDateBefore;
	}

	/**
	 * @param shipmentDateBefore the shipmentDateBefore to set
	 */
	public void setShipmentDateBefore(String shipmentDateBefore) {
		this.shipmentDateBefore = shipmentDateBefore;
	}

	/**
	 * @return the shipmentDateAfter
	 */
	public String getShipmentDateAfter() {
		return shipmentDateAfter;
	}

	/**
	 * @param shipmentDateAfter the shipmentDateAfter to set
	 */
	public void setShipmentDateAfter(String shipmentDateAfter) {
		this.shipmentDateAfter = shipmentDateAfter;
	}

	/**
	 * @return the createdByValue
	 */
	public String getCreatedByValue() {
		return createdByValue;
	}

	/**
	 * @param createdByValue the createdByValue to set
	 */
	public void setCreatedByValue(String createdByValue) {
		this.createdByValue = createdByValue;
	}

	/**
	 * @return the updatedByValue
	 */
	public String getUpdatedByValue() {
		return updatedByValue;
	}

	/**
	 * @param updatedByValue the updatedByValue to set
	 */
	public void setUpdatedByValue(String updatedByValue) {
		this.updatedByValue = updatedByValue;
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

	public Long getId() {
		return id;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the groupDescription
	 */
	public String getGroupDescription() {
		return groupDescription;
	}

	/**
	 * @param groupDescription the groupDescription to set
	 */
	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	/**
	 * @return the featureIds
	 */
	public String getFeatureIds() {
		return featureIds;
	}

	/**
	 * @param featureIds the featureIds to set
	 */
	public void setFeatureIds(String featureIds) {
		this.featureIds = featureIds;
	}

	/**
	 * @return the noOfUserAttached
	 */
	public Integer getNoOfUserAttached() {
		return noOfUserAttached;
	}

	/**
	 * @param noOfUserAttached the noOfUserAttached to set
	 */
	public void setNoOfUserAttached(Integer noOfUserAttached) {
		this.noOfUserAttached = noOfUserAttached;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserGroup [groupName=" + groupName + ", groupDescription=" + groupDescription + ", featureIds="
				+ featureIds + ", noOfUserAttached=" + noOfUserAttached + ", createdBy=" + createdBy + ", createdAt="
				+ createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
	}
	
	
	
}