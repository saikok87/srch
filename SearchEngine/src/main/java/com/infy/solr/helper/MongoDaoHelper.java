package com.infy.solr.helper;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class MongoDaoHelper {
	

	public void doMongoProcessing() { 
	/**** Connect to MongoDB ****/
	// Since 2.10.0, uses MongoClient
	try {
		MongoClient mongo = new MongoClient("localhost",27017);
		
		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		DB db = mongo.getDB("testdb");
		
		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		DBCollection table = db.getCollection("user");
		
		/**** Insert ****/
		// create a document to store key and value
	/*	BasicDBObject document = new BasicDBObject();
		document.put("name", "nilesh");
		document.put("age", 25);
		document.put("createdDate", new Date());
		table.insert(document);
		*/
		/**** Find and display ****/
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", "sai");
		
		//DBCursor cursor = table.find(searchQuery);
		DBCursor cursor = table.find();
		
		while(cursor.hasNext()) {
			//System.out.println(cursor.next());
			BasicDBObject record = (BasicDBObject) cursor.next();
			System.out.println("id: " + record.get("_id"));
		}
		
		/**** Update ****//*
		// search document where name="mkyong" and update it with new values
		BasicDBObject query = new BasicDBObject();
		
		query.put("name", "sai");
		
		BasicDBObject newdoc = new BasicDBObject();
		newdoc.put("name", "sai-updated");
		
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.put("$set", newdoc);
		
		table.update(query, updateObj);
		
		*//**** Find and display ****//*
		BasicDBObject searchQuery2 = new BasicDBObject().append("name", "sai-updated");
		
		DBCursor cursor2 = table.find(searchQuery2);
		
		while(cursor2.hasNext()) {
			System.out.println(cursor2.next());
		}
		
		*//**** Done ****//*
		System.out.println("Done");*/
		
	} catch (UnknownHostException e) {
		e.printStackTrace();
	} catch (MongoException e) {
		e.printStackTrace();
    }
	
  }	
}
