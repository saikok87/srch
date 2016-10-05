package com.infy.solr.helper;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infy.solr.constants.AppConstants;
import com.infy.solr.dao.MongoTweeterDao;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.SolrTweeterDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Component
public class SolrDaoProcessing {
	
	@Autowired
	private MongoTweeterDao mongoTweeterDao;
	
	@Qualifier("solrHelper")
	@Autowired
	private SolrDaoHelper<SolrTweeterDTO> solrDao;
	
	public ContextDTO readMongoAndIndexSolr(ContextDTO contextDTO) {
		try {
			MongoClient client = new MongoClient("localhost",27017);
			
			DB db = client.getDB("tweeterdb");
			
			DBCollection table = db.getCollection("tweets");
			
			BasicDBObject searchQuery = new BasicDBObject();
			
			DBCursor cursor = table.find();
			
			Collection<SolrTweeterDTO> solrTweeterDTOs = new ArrayList<SolrTweeterDTO>();
			
			
			while(cursor.hasNext()) {
				BasicDBObject record = (BasicDBObject) cursor.next(); 
				System.out.println("Mongo record: " + record.toString());
				
				solrTweeterDTOs.add(new SolrTweeterDTO(record.get("_id").toString(), record.get("tweetData").toString()));
				contextDTO.put(AppConstants.TWEET_FEED, solrTweeterDTOs);
				contextDTO = solrDao.put(contextDTO);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return contextDTO;
		
	}

	public void readSolrTweeterDTOs(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		QueryResponse rsp = solrDao.readAll();
		List<SolrTweeterDTO> beans = rsp.getBeans(SolrTweeterDTO.class);
		for(SolrTweeterDTO bean: beans){
			System.out.println("Read SolrTweeterDTO " + bean.getId() + ", name = " + bean.getTweeterData());
		}
		
	}

	/*private void addSolrTweeterDTOs(solrDaoHelper<SolrTweeterDTO> solrDao) {
		Collection<SolrTweeterDTO> users = new ArrayList<SolrTweeterDTO> (3);
		users.add(new SolrTweeterDTO("1", "SolrTweeterDTO 1"));
		users.add(new SolrTweeterDTO("2", "SolrTweeterDTO 2"));
		users.add(new SolrTweeterDTO("3", "SolrTweeterDTO 3"));
		solrDao.put(SolrTweeterDTOs);
	}*/

	public void readDocuments(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		SolrDocumentList docs = solrDao.readAllDocs();
		Iterator<SolrDocument> itr = docs.iterator();
		int count=10;
		
		while(itr.hasNext() && count-- >0){
			SolrDocument resultDoc = itr.next();
			
			String content = (String) resultDoc.getFieldValue("content");
			String id = (String) resultDoc.getFieldValue("id"); //unique field
			System.out.println ("Read " + resultDoc + " with id = " + id + " and content = " + content);
		}
		
	}
	
	public ContextDTO readHighLightedPage(ContextDTO contextDTO) {
		try {
			
			List<SolrTweeterDTO> tweetList = solrDao.testHighlightAndPagination(1,14); // passing pageNum=1 and no. of rows/page=14
			
			for(SolrTweeterDTO tweets : tweetList) {
				System.out.println("id: " + tweets.getId());
				System.out.println("TweeterData: " + tweets.getTweeterData());
			}
			
			contextDTO.put(AppConstants.TWEET_LIST, tweetList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return contextDTO;	
	}

	public void addDocuments(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for(int i=0; i<1000; i++)
			docs.add(getRandomSolrDoc(i));
		
		solrDao.putDoc(docs);
	}

	public SolrInputDocument getRandomSolrDoc(int count) {
		SolrInputDocument doc =  new SolrInputDocument();
		doc.addField("id", "id"+count);
		doc.addField("name", "doc"+count);
		
		return doc;
	}
	

}
