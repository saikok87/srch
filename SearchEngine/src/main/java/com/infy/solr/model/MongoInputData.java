package com.infy.solr.model;

import org.springframework.stereotype.Component;

@Component
public class MongoInputData {
	
	private String tweeterData;

	public String getTweeterData() {
		return tweeterData;
	}

	public void setTweeterData(String tweeterData) {
		this.tweeterData = tweeterData;
	}
	
	
}
