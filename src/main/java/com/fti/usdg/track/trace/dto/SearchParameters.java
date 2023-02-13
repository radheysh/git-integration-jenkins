/**
 * 
 */
package com.fti.usdg.track.trace.dto;

import java.util.List;


/**
 * @author Anup
 *
 */
public class SearchParameters {

	private Integer pageNumber = null;
	private Integer perPage = null;	 
	private String lookup = null;
	private List<Filter> filters  = null;
	private SortKey sortKey = null;
 

	/**
	 * @return the pageNumber
	 */
	public Integer getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the perPage
	 */
	public Integer getPerPage() {
		return perPage;
	}
	/**
	 * @param perPage the perPage to set
	 */
	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}
	/**
	 * @return the lookup
	 */
	public String getLookup() {
		return lookup;
	}
	/**
	 * @param lookup the lookup to set
	 */
	public void setLookup(String lookup) {
		this.lookup = lookup;
	}
	/**
	 * @return the filters
	 */
	public List<Filter> getFilters() {
		return filters;
	}
	/**
	 * @param filters the filters to set
	 */
	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}
	/**
	 * @return the sortKey
	 */
	public SortKey getSortKey() {
		return sortKey;
	}
	/**
	 * @param sortKey the sortKey to set
	 */
	public void setSortKey(SortKey sortKey) {
		this.sortKey = sortKey;
	}
	@Override
	public String toString() {
		return "SearchCriteria [pageNumber=" + pageNumber + ", perPage=" + perPage + ", lookup=" + lookup + ", filters="
				+ filters + ", sortKey=" + sortKey + "]";
	}
	 
	
}
