package com.api.util;

public enum EndPointURL {
	
	
	PING("/ping/"),
	GET_ALL("/all"),
	GET_BY_ID("/find/"),
	POST("/add"),
	PUT("/update"),
	DELETE("/delete/");
	
	String resourcePath;
	
	private EndPointURL(String resourcePath) {
		this.resourcePath=resourcePath;
	}
	
	public String getResourcePath() {
		return this.resourcePath;
	}
	
	public String getResourcePath(String data) {
		return this.resourcePath+data;
	}

}
