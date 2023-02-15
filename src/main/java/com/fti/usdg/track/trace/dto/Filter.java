/**
 * 
 */
package com.fti.usdg.track.trace.dto;

/**
 * @author Anup Kumar Gupta
 * @Date Jan 05, 2019 9:55:15 AM *
 */
public class Filter {

	private String type = null;
	private String name = null;
	private String value = null;

	public Filter() {

	}

	public Filter(String name, String value) {
		super();

		this.name = name;
		this.value = value;
	}

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
