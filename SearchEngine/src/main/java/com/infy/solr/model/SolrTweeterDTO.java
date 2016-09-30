package com.infy.solr.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.stereotype.Component;

@Component
public class SolrTweeterDTO {
	
	@Field("id")
	String id;
	
	@Field("tweeterData")
	String tweeterData;
	
	public SolrTweeterDTO() {}
	
	public SolrTweeterDTO(String id, String tweeterData) {
		super();
		this.id=id;
		this.tweeterData=tweeterData;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTweeterData() {
		return tweeterData;
	}

	public void setTweeterData(String tweeterData) {
		this.tweeterData = tweeterData;
	}
	
    
}
