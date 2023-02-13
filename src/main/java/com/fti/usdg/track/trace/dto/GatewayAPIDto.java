/**
 * 
 */
package com.fti.usdg.track.trace.dto;

import java.util.Arrays;

/**
 * @author Anup
 *
 */
public class GatewayAPIDto {

	private String channelName = null;
	private String methodName = null;
	private String [] parameters = null;
	private Boolean isReadOps = null;
	private String chaincodeName = null;
	
	
	
	/**
	 * @return the chaincodeName
	 */
	public String getChaincodeName() {
		return chaincodeName;
	}
	/**
	 * @param chaincodeName the chaincodeName to set
	 */
	public void setChaincodeName(String chaincodeName) {
		this.chaincodeName = chaincodeName;
	}
	/**
	 * @return the isReadOps
	 */
	public Boolean getIsReadOps() {
		return isReadOps;
	}
	/**
	 * @param isReadOps the isReadOps to set
	 */
	public void setIsReadOps(Boolean isReadOps) {
		this.isReadOps = isReadOps;
	}
	/**
	 * @return the channelName
	 */
	public String getChannelName() {
		return channelName;
	}
	/**
	 * @param channelName the channelName to set
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the parameters
	 */
	public String[] getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		return "GatewayAPIDto [channelName=" + channelName + ", methodName=" + methodName + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}
}
