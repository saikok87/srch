package com.infy.solr.model;

import org.springframework.stereotype.Component;

@Component
public class SearchResponse {
	
	private String responseTime;
	
	private String response;

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
