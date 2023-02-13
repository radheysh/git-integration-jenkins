/**
 * 
 */
package com.fti.usdg.track.trace.dto;

import java.util.Set;

/**
 * @author Anup
 *
 */
// API for All shippers, all Commodity Name, all Crude Type
public class FilterDataDto {

	private Set<String> shippers = null;
	private Set<String> crudTypes = null;
	private Set<String> commodityNames = null;
	
	/**
	 * @return the shippers
	 */
	public Set<String> getShippers() {
		return shippers;
	}
	/**
	 * @param shippers the shippers to set
	 */
	public void setShippers(Set<String> shippers) {
		this.shippers = shippers;
	}
	/**
	 * @return the crudTypes
	 */
	public Set<String> getCrudTypes() {
		return crudTypes;
	}
	/**
	 * @param crudTypes the crudTypes to set
	 */
	public void setCrudTypes(Set<String> crudTypes) {
		this.crudTypes = crudTypes;
	}
	/**
	 * @return the commodityNames
	 */
	public Set<String> getCommodityNames() {
		return commodityNames;
	}
	/**
	 * @param commodityNames the commodityNames to set
	 */
	public void setCommodityNames(Set<String> commodityNames) {
		this.commodityNames = commodityNames;
	}
	
	
	
}
