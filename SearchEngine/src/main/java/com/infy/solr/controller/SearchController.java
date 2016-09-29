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

public class SearchController extends SpringBeanAutowiringSupport implements ISearchController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
	
	@Autowired
	private SearchServicesFacade searchServicesFacade;
	
	@Override
	public Response solrSearch(SearchData searchData) {
		Response response;
		
		ContextDTO contextDTO = new ContextDTO();
		contextDTO.put(AppConstants.SOLR_SEARCH_INPUT, searchData);
		searchServicesFacade.doSolrSearching(contextDTO);
		
		return null;
	}

	@Override
	public Response MongoInsertion(MongoInputData mongoInputData) {
		// TODO Auto-generated method stub
		return null;
	}
			
}
