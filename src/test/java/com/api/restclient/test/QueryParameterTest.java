package com.api.restclient.test;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;
import org.testng.annotations.Test;

import com.api.client.RestClient;
import com.api.util.RestApiResponse;

public class QueryParameterTest {
	
	
	@Test
	public void validateQueryPath() throws URISyntaxException {
		
		URI uri = new URIBuilder()
				.setScheme("http")
				.setHost("localhost:8080")
				.setPath("api/query")
				.setParameter("id", "101")
				.setParameter("Name", "Tom")
				.setParameter("Category", "Books")
				.build();
		
		System.out.println(uri);
		
		RestApiResponse response = RestClient.getRequest(uri, null);
		
				
	}

}
