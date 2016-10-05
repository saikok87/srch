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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.infy.solr.constants.AppConstants;
import com.infy.solr.dao.MongoTweeterDao;
import com.infy.solr.helper.SolrDaoHelper;
import com.infy.solr.helper.SolrDaoProcessing;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.SolrTweeterDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Component
public class SearchServicesFacade {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SearchServicesFacade.class);
	
	@Autowired
	private SolrDaoProcessing solrDaoProcessing;
	
	@Qualifier("solrHelper")
	@Autowired
	private SolrDaoHelper<SolrTweeterDTO> solrDao;
	
	String solrURL = "http://localhost:8983/solr/tweetcollection";
	
	public ContextDTO doSolrSearching(ContextDTO contextDTO) {
		System.out.println("Starting off " + this.getClass().toString());
		contextDTO = solrDaoProcessing.readHighLightedPage(contextDTO); // read solr searched result in highlighted format + pagination
		 
	    return contextDTO;
	}
	
	public ContextDTO doSolrIndexing(ContextDTO contextDTO) {
		
		System.out.println("Starting off " + this.getClass().toString());
		contextDTO = solrDaoProcessing.readMongoAndIndexSolr(contextDTO); // read mongo data and add it to solr Index
		
		return contextDTO;
		
	}
	
	
}
