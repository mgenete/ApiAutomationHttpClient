package com.api.restclient.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.base.TestBase;
import com.api.client.RestClient;
import com.api.util.EndPointURL;
import com.api.util.RestApiResponse;

public class DeleteMethodTest extends TestBase{

	RestApiResponse response;
	private String serviceURL;
	
	@BeforeMethod
	public void setUp() {
		serviceURL = prop.getProperty("URL");
	}
	
	@Test
	public void deleteById() {
		
		String urlPost = serviceURL+EndPointURL.POST.getResourcePath("101");
		String urlDelete = serviceURL+EndPointURL.DELETE.getResourcePath("101");
		String urlGet = serviceURL+EndPointURL.GET_BY_ID.getResourcePath("101");
		String jsonBody = "";
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		
		response = RestClient.postRequest(urlPost, jsonBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response = RestClient.deleteRequest(urlDelete, null);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response = RestClient.getRequest(urlGet, null);
		Assert.assertTrue((HttpStatus.SC_NOT_FOUND == response.getStatusCode()), "Expected status code not found");
	
	}
}
