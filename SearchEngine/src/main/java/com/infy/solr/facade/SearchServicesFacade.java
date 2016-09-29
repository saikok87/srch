package com.infy.solr.facade;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.infy.solr.dao.MongoTweeterDao;
import com.infy.solr.helper.SolrDaoHelper;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.SolrTweeterDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class SearchServicesFacade {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SearchServicesFacade.class);
	
	@Autowired
	private MongoTweeterDao mongoTweeterDao;
	
	String solrURL = "http://localhost:8983/solr/high2collection";
	
	public ContextDTO doSolrSearching(ContextDTO contextDTO) {
		System.out.println("Starting off " + this.getClass().toString());
		SolrDaoHelper<SolrTweeterDTO> solrDao = new SolrDaoHelper<SolrTweeterDTO> (solrURL);
		
		readMongoAndIndexSolr(solrDao); // read mongo data and add it to solr Index
		//readHighLightedPage(solrDao); // read solr searched result in highlighted format + pagination
		 
	    //addDocuments (solrDao);
        //readDocuments (solrDao); // to read useres in sorted order of id
        
        // addSolrTweeterDTOs (solrDao);
        // readSolrTweeterDTOs (solrDao); 
        
		return contextDTO;
	}
	
	private void readMongoAndIndexSolr(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		try {
			MongoClient client = new MongoClient("localhost",27017);
			
			DB db = client.getDB("testdb");
			
			DBCollection table = db.getCollection("user");
			
			BasicDBObject searchQuery = new BasicDBObject();
			//searchQuery.put("name", "sai");
			
			DBCursor cursor = table.find();
			
			Collection<SolrTweeterDTO> solrTweeterDTOs = new ArrayList<SolrTweeterDTO>();
			
			while(cursor.hasNext()) {
				BasicDBObject record = (BasicDBObject) cursor.next(); 
				System.out.println("Mongo record: " + record.toString());
				
				solrTweeterDTOs.add(new SolrTweeterDTO(record.get(mongoTweeterDao.get_id()).toString(), record.get(mongoTweeterDao.getTweetData()).toString()));
				solrDao.put(solrTweeterDTOs);
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void readSolrTweeterDTOs(SolrDaoHelper<SolrTweeterDTO> solrDao) {
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

	private void readDocuments(SolrDaoHelper<SolrTweeterDTO> solrDao) {
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
	
	private void readHighLightedPage(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		try {
			List<SolrTweeterDTO> usersList = solrDao.testHighlightAndPagination(1,14); // passing pageNum=1 and no. of rows/page=14
			for(SolrTweeterDTO user : usersList) {
				System.out.println("id: " + user.getId());
				System.out.println("TweeterData: " + user.getTweeterData());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void addDocuments(SolrDaoHelper<SolrTweeterDTO> solrDao) {
		Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		for(int i=0; i<1000; i++)
			docs.add(getRandomSolrDoc(i));
		
		solrDao.putDoc(docs);
	}

	private SolrInputDocument getRandomSolrDoc(int count) {
		SolrInputDocument doc =  new SolrInputDocument();
		doc.addField("id", "id"+count);
		doc.addField("name", "doc"+count);
		
		return doc;
	}
}
