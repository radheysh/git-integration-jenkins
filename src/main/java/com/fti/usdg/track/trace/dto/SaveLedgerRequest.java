/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup
 *
 */
public class SaveLedgerRequest {
	
	private String fcn = null;
	private String args = null;
	
	public SaveLedgerRequest() {
		super();
	}	
	
	public SaveLedgerRequest(String fcn, String args) {
		super();
		this.fcn = fcn;
		this.args = args;
	}
	/**
	 * @return the fcn
	 */
	public String getFcn() {
		return fcn;
	}
	/**
	 * @param fcn the fcn to set
	 */
	public void setFcn(String fcn) {
		this.fcn = fcn;
	}
	/**
	 * @return the args
	 */
	public String getArgs() {
		return args;
	}
	/**
	 * @param args the args to set
	 */
	public void setArgs(String args) {
		this.args = args;
	}
	
	
}
