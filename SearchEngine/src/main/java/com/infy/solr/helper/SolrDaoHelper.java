package com.infy.solr.helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Component;

import com.infy.solr.constants.AppConstants;
import com.infy.solr.model.ContextDTO;
import com.infy.solr.model.SolrTweeterDTO;
import com.infy.solr.util.SolrServerFactory;


public class SolrDaoHelper<T> {

	HttpSolrServer server = null;

	public SolrDaoHelper() {
	}
	
	public SolrDaoHelper(String solrURL) {
		server = (HttpSolrServer) SolrServerFactory.getInstance().createServer(
				solrURL);
		configureSolr(server);
	}

	/*public void put(T dao) {
		put(createSingletonSet(dao));
	}*/

	public ContextDTO put(ContextDTO contextDTO) {
		
		try {
			Collection<SolrTweeterDTO> solrTweeterDTOs = (Collection<SolrTweeterDTO>) contextDTO.get(AppConstants.TWEET_FEED);
			
			UpdateResponse rsp = server.addBeans(solrTweeterDTOs);
			System.out.println("Added TweeterFeed to solr. Time taken = "
					+ rsp.getElapsedTime() + ". " + rsp.toString());
			contextDTO.put(AppConstants.SOLR_RESPONSE, rsp);
			
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contextDTO;

	}
	
	public void putDoc(SolrInputDocument doc)	{
		putDoc(createSingletonSet(doc));
	}
	

	public void putDoc(Collection<SolrInputDocument> docs) {
		
		try {
			long startTime = System.currentTimeMillis();
			UpdateRequest req = new UpdateRequest();
			req.setAction(UpdateRequest.ACTION.COMMIT, false, false);
			req.add(docs);
			UpdateResponse rsp = req.process(server);
			System.out.print ("Added documents to solr. Time taken = " + rsp.getElapsedTime() + ". " + rsp.toString());
			long endTime = System.currentTimeMillis();
			System.out.println (" , time-taken=" + ((double)(endTime-startTime))/1000.00 + " seconds");
			
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public QueryResponse readAll(){
		SolrQuery query = new SolrQuery();
		query.set("*:*");
		//query.addSortField( "price", SolrQuery.ORDER.asc );
		
		QueryResponse rsp = null;
		
		try {
			rsp = server.query(query);
		} catch (SolrServerException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return rsp;
	}

	public SolrDocumentList readAllDocs() {
		SolrQuery query = new SolrQuery();
        query.setQuery( "*:*" );
      //solr sorting 
      //  query.addSort("id", ORDER.desc); 
        query.addNumericRangeFacet("age", 21, 27, 1);
      
        //query.addSortField( "price", SolrQuery.ORDER.asc );
        QueryResponse rsp = null;
        try {
            rsp = server.query(query);
        } catch (SolrServerException e) 
        {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
			
			e.printStackTrace();
		}
        
        SolrDocumentList docs = rsp.getResults();
        
		return docs;
		
	}
	
	public List<SolrTweeterDTO> testHighlightAndPagination(int pageNum, int numItemsPerPage) throws Exception {
		SolrQuery query =  new SolrQuery();
		//solr pagination
		query.setStart((pageNum - 1) * numItemsPerPage);
		query.setRows(numItemsPerPage);
		//solr pagination ends
		
		//solr highlighting
		query.setQuery("tweeterData:*");
		query.setHighlightFragsize(0);
		query.setHighlight(true);
		query.addHighlightField("tweeterData");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//solr highlighting ends
		
		QueryResponse resp = server.query(query);
		
		System.out.println(resp.toString());
		
		//Then to get back the highlight results you need something like this:
		
		SolrDocumentList docList= resp.getResults();
		
		//Object the result set
		List<SolrTweeterDTO> tweetDatasList = new ArrayList<SolrTweeterDTO>();
	
		String tmpId = "";
		
		Map<String,Map<String,List<String>>> highlightMap = resp.getHighlighting();
		
		for(SolrDocument solrDoc : docList) {
			SolrTweeterDTO tweets = new SolrTweeterDTO();
			tmpId=solrDoc.getFieldValue("id").toString();
			tweets.setId(tmpId);
			tweets.setTweeterData(solrDoc.getFieldValue("tweeterData").toString());
			List<String> tweetList = highlightMap.get(tmpId).get("tweeterData");
			
			if(tweetList!=null && tweetList.size()>0) {
				tweets.setTweeterData(tweetList.get(0));
			} else {
				System.out.println("Names list is null");
			}
			
			tweetDatasList.add(tweets);
			
		}
		return tweetDatasList;
		
	}
	
	private <U> Collection<U> createSingletonSet(U dao) {
		if (dao == null)
			return Collections.emptySet();

		return Collections.singleton(dao);
	}

	private void configureSolr(HttpSolrServer server) {
		server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
		server.setConnectionTimeout(5000); // 5 seconds to establish TCP
		// The following settings are provided here for completeness.
		// They will not normally be required, and should only be used
		// after consulting javadocs to know whether they are truly required.
		/*server.setSoTimeout(1000); // socket read timeout
		server.setDefaultMaxConnectionsPerHost(100);
		server.setMaxTotalConnections(100);
		server.setFollowRedirects(false); // defaults to false
*/		// allowCompression defaults to false.
		// Server side must support gzip or deflate for this to have any effect.
		server.setAllowCompression(false);
	}


}
