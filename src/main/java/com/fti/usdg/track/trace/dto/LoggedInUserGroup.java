/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup Kumar Gupta
 *
 */
public class LoggedInUserGroup {
	
	public String loggedInGroup  = null;
	public String targetGroup  = null;
	public String featureIds  = null;
	public String token  = null;
	public String uuid  = null;
	public String password  = null;
	public String username  = null;
	public Boolean switchback  = null;
	 
	
	/**
	 * @return the switchback
	 */
	public Boolean getSwitchback() {
		return switchback;
	}
	/**
	 * @param switchback the switchback to set
	 */
	public void setSwitchback(Boolean switchback) {
		this.switchback = switchback;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the loggedInGroup
	 */
	public String getLoggedInGroup() {
		return loggedInGroup;
	}
	/**
	 * @param loggedInGroup the loggedInGroup to set
	 */
	public void setLoggedInGroup(String loggedInGroup) {
		this.loggedInGroup = loggedInGroup;
	}
	/**
	 * @return the targetGroup
	 */
	public String getTargetGroup() {
		return targetGroup;
	}
	/**
	 * @param targetGroup the targetGroup to set
	 */
	public void setTargetGroup(String targetGroup) {
		this.targetGroup = targetGroup;
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
	@Override
	public String toString() {
		return "LoggedInUserGroup [loggedInGroup=" + loggedInGroup + ", targetGroup=" + targetGroup + ", featureIds="
				+ featureIds + ", token=" + token + ", uuid=" + uuid + ", password=" + password + ", username="
				+ username + ", switchback=" + switchback + "]";
	}

	
	
}
