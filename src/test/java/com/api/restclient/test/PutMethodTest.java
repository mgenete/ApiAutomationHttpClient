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

public class PutMethodTest extends TestBase{

	
	String serviceURL;
	RestApiResponse response;
	
	@BeforeMethod
	public void setUp() {
		serviceURL = prop.getProperty("URL");
	}
	
	@Test
	public void validateUpdateWithValidId() {
		
		String urlPost = serviceURL+EndPointURL.POST.getResourcePath("101");
		String urlPut = serviceURL+EndPointURL.PUT.getResourcePath("101");
		String urlGet = serviceURL+EndPointURL.GET_BY_ID.getResourcePath("101");
		String jsonBody = "";
		String jsonBodyUpdate = "";
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		
		response = RestClient.postRequest(urlPost, jsonBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		headers.clear();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		
		response = RestClient.putRequest(urlPut, jsonBodyUpdate, ContentType.APPLICATION_JSON, headers);
		Assert.assertTrue((HttpStatus.SC_OK == response.getStatusCode()), "Expected status code not found");
		
		response = RestClient.getRequest(urlGet, null);
		Assert.assertTrue((HttpStatus.SC_OK == response.getStatusCode()), "Expected status code not found");
	}
	
	@Test
	public void validateUpdateWithInvaidsId() {
		String urlUpdate = serviceURL+EndPointURL.POST.getResourcePath("101");
		String jsonBodyUpdate = "";
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		
		response = RestClient.putRequest(urlUpdate, jsonBodyUpdate, ContentType.APPLICATION_JSON, headers);
		Assert.assertTrue((HttpStatus.SC_NOT_FOUND == response.getStatusCode()), "Expected status code not found");
		
	}
}
