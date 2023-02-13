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
@Table(name = "application_feature")
public class ApplicationFeature {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String featureId = null;
	private String featureText = null;
	private String status= null;
	private String featureDescription = null;
	private String type = null;
	
	
	
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the featureDescription
	 */
	public String getFeatureDescription() {
		return featureDescription;
	}
	/**
	 * @param featureDescription the featureDescription to set
	 */
	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the featureId
	 */
	public String getFeatureId() {
		return featureId;
	}
	/**
	 * @param featureId the featureId to set
	 */
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}
	/**
	 * @return the featureText
	 */
	public String getFeatureText() {
		return featureText;
	}
	/**
	 * @param featureText the featureText to set
	 */
	public void setFeatureText(String featureText) {
		this.featureText = featureText;
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