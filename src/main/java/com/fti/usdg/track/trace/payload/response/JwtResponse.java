package com.fti.usdg.track.trace.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;

	private String uuid;
	private String groupName;
	private String featureIds = null;
	private String originalGroup = null;
	

	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles
			) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		
	}

	public JwtResponse(String accessToken, Long id, String username, String email, String uuid, String groupName,
			List<String> roles,String featureIds) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.uuid = uuid;
		this.groupName = groupName;
		this.featureIds = featureIds;
	}

	public JwtResponse(String accessToken, Long id, String username, String email, String uuid, String groupName,
			 String featureIds, String originalGroup) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.uuid = uuid;
		this.groupName = groupName;
		this.featureIds = featureIds;
		this.originalGroup = originalGroup;
	}
	
	
	/**
	 * @return the originalGroup
	 */
	public String getOriginalGroup() {
		return originalGroup;
	}

	/**
	 * @param originalGroup the originalGroup to set
	 */
	public void setOriginalGroup(String originalGroup) {
		this.originalGroup = originalGroup;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
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

}
