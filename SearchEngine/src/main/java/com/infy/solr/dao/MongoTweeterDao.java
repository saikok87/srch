package com.infy.solr.dao;

import org.springframework.stereotype.Component;

@Component
public class MongoTweeterDao {
	
	private String _id;
	private String tweetData;
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getTweetData() {
		return tweetData;
	}
	public void setTweetData(String tweetData) {
		this.tweetData = tweetData;
	}
	
	
}
