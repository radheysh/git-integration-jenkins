/**
 * 
 */
package com.fti.usdg.track.trace.common;

import java.util.Collection;

/**
 * @author Anup
 *
 */
public class TrackTraceResponse {
	
	private String responseCode = null;
	private String message = null;
	private Collection<?> response = null;
	
	public TrackTraceResponse() {
	}
	
	public TrackTraceResponse(String responseCode,String info) {
		this.message=info;
		this.responseCode=responseCode;
	}
	
	public TrackTraceResponse(String responseCode,String info,Collection<?> response) {
		this.message=info;
		this.responseCode=responseCode;
		this.response=response;
	}
	
	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	 
	public Collection<?> getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(Collection<?> response) {
		this.response = response;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
}
