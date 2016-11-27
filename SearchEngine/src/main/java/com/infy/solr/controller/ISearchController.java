package com.infy.solr.controller;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.infy.solr.model.MongoInputData;
import com.infy.solr.model.SearchData;
import com.infy.solr.model.SolrTweeterDTO;

@Path("v1")
public interface ISearchController {
	
	@POST
	@Path("/solr/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SolrTweeterDTO> solrSearch(SearchData searchData);
	
	@POST
	@Path("/mongo/insert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response MongoInsertion(MongoInputData mongoInputData);
	
	@POST
	@Path("/solr/indexing")
	@Produces(MediaType.APPLICATION_JSON)
	public Response solrIndexing();
	
	@GET
	@Path("/test")
	public String test();
	
	
}
