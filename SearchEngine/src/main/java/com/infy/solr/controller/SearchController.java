package com.infy.solr.controller;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.infy.solr.constants.AppConstants;
import com.infy.solr.facade.SearchServicesFacade;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.MongoInputData;
import com.infy.solr.model.SearchData;
import com.infy.solr.model.SearchResponse;

public class SearchController extends SpringBeanAutowiringSupport implements ISearchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchServicesFacade searchServicesFacade;
	
	@Override
	public Response solrSearch(SearchData searchData) {
		Response response;
		
		ContextDTO contextDTO = new ContextDTO();
		contextDTO.put(AppConstants.SOLR_SEARCH_INPUT, searchData);
		contextDTO = searchServicesFacade.doSolrSearching(contextDTO);
		
		response = Response.ok((contextDTO.get(AppConstants.SOLR_RESPONSE)).toString()).build();
		
		return response;
	}

	@Override
	public Response MongoInsertion(MongoInputData mongoInputData) {
		// TODO Auto-generated method stub
		return null;
	}
			
}
