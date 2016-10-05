package com.infy.solr.controller;

import java.util.List;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.infy.solr.constants.AppConstants;
import com.infy.solr.facade.SearchServicesFacade;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.MongoInputData;
import com.infy.solr.model.SearchData;
import com.infy.solr.model.SearchResponse;
import com.infy.solr.model.SolrTweeterDTO;

@Component
public class SearchController extends SpringBeanAutowiringSupport implements ISearchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchServicesFacade searchServicesFacade;
	
	@Override
	public List<SolrTweeterDTO> solrSearch(SearchData searchData) {
		Response response;
		
		ContextDTO contextDTO = new ContextDTO();
		contextDTO.put(AppConstants.SOLR_SEARCH_INPUT, searchData);
		contextDTO = searchServicesFacade.doSolrSearching(contextDTO);
		
		
		//response = Response.ok((contextDTO.get(AppConstants.TWEET_LIST)).toString()).build();
		
		return (List<SolrTweeterDTO>) contextDTO.get(AppConstants.TWEET_LIST);
	}

	@Override
	public Response MongoInsertion(MongoInputData mongoInputData) {
		
		return null;
	}

	@Override
	public Response solrIndexing() {
		Response response;
		
		ContextDTO contextDTO = new ContextDTO();
		contextDTO = searchServicesFacade.doSolrIndexing(contextDTO);
		
		response = Response.ok((contextDTO.get(AppConstants.SOLR_RESPONSE)).toString()).build();
		
		return response;
	}
			
}
