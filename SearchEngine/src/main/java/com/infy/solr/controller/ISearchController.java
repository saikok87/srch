package com.infy.solr.controller;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infy.solr.model.MongoInputData;
import com.infy.solr.model.SearchData;

@Path("v1")
public interface ISearchController {
	
	@POST
	@Path("/solr/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response solrSearch(SearchData searchData);
	
	@POST
	@Path("/mongo/insert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response MongoInsertion(MongoInputData mongoInputData);
	

}
