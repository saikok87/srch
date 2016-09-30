package com.infy.solr.model;

import org.springframework.stereotype.Component;

@Component
public class SearchData {
	
	private String searchInput;

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	@Override
	public String toString() {
		return "RequestData [searchInput=" + searchInput + "]";
	}
	
	
}
