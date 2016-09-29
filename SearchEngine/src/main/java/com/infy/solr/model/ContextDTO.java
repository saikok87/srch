package com.infy.solr.model;

import java.util.HashMap;
import java.util.Map;

public class ContextDTO {
	
	private Map<String, Object> contextMap;
	
	public ContextDTO() {
		contextMap = new HashMap<String, Object>();
	}
	
	public Object get(String name) {
		return contextMap.get(name);
	}

	public void put(String name, Object obj) {
		contextMap.put(name, obj);
    }

	public boolean contains(String name) {
		return contextMap.containsKey(name);
	}
	
	public void remove(String name) {
		contextMap.remove(name);
	}
	

}
