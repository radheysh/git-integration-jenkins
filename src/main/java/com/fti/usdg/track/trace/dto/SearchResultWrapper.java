/**
 * 
 */
package com.fti.usdg.track.trace.dto;

import java.util.List;

/**
 * @author Anup
 *
 */
public class SearchResultWrapper {

	private Long totalCount = null;
	private List<?> searchResult = null;
	/**
	 * @return the totalCount
	 */
	public Long getTotalCount() {
		return totalCount;
	}
	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * @return the searchResult
	 */
	public List<?> getSearchResult() {
		return searchResult;
	}
	/**
	 * @param searchResult the searchResult to set
	 */
	public void setSearchResult(List<?> searchResult) {
		this.searchResult = searchResult;
	}
	
	public SearchResultWrapper() {}
	/**
	 * @param totalCount
	 * @param searchResult
	 */
	public SearchResultWrapper(Long totalCount, List<?> searchResult) {
		super();
		this.totalCount = totalCount;
		this.searchResult = searchResult;
	}
	
	 
	
}

