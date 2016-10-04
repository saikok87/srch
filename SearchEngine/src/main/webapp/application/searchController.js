function search($searchVal) {
	console.log("inside search page");
	
	var searchInput = $('#searchVal').val();
	
	var getSearchUrl = window.location.protocol+"//"+window.location.host+"/srch/api/v1/solr/search";
	
	var postRequestForSearchAPI = {
			 "searchInput": searchInput
	  		};
	
	$.ajax({type: "POST",
	        url: getSearchUrl,
	        dataType:"json",
	        data : JSON.stringify(postRequestForSearchAPI),
	        headers : {"Access-Control-Allow-Origin":"*",'Content-Type': 'application/json' },	           
	        success:function(response){ 
	        	ajaxResponse = response;
	        	console.log("Search is sucessfull");
	        	handlingAjaxResponse(ajaxResponse);
	            },
	        error:function(){  
	           ajaxResponse = "Error while calling Search Engine service";
	           handlingAjaxResponse(ajaxResponse);
	           }             
	       });
	
}

function handlingAjaxResponse(ajaxResponse){
	var contentDiv = document.getElementById('web-content');
    $('.jsonArea').show();
    //$('#result').html(library.json.prettyPrint(ajaxResponse));
    $('#contentDiv').html(angular.toJson(ajaxResponse, true));
    console.log(ajaxResponse);
}


