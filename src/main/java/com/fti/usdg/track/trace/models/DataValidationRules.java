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
@Table(name = "data_validation_rules")
public class DataValidationRules {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String attributeName = null;
	private String attibuteType = null;
	private String minRange= null;
	private String maxRange= null;
	private String unit= null;
	private String errorMessage= null;
	private String updatedDate= null;
	private String updatedBy= null;
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
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	/**
	 * @return the attibuteType
	 */
	public String getAttibuteType() {
		return attibuteType;
	}
	/**
	 * @param attibuteType the attibuteType to set
	 */
	public void setAttibuteType(String attibuteType) {
		this.attibuteType = attibuteType;
	}
	/**
	 * @return the minRange
	 */
	public String getMinRange() {
		return minRange;
	}
	/**
	 * @param minRange the minRange to set
	 */
	public void setMinRange(String minRange) {
		this.minRange = minRange;
	}
	/**
	 * @return the maxRange
	 */
	public String getMaxRange() {
		return maxRange;
	}
	/**
	 * @param maxRange the maxRange to set
	 */
	public void setMaxRange(String maxRange) {
		this.maxRange = maxRange;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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
	
	
	 
	
}