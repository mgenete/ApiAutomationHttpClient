package com.api.httpsclient.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.base.TestBase;
import com.api.client.HttpsClient;
import com.api.util.EndPointURL;
import com.api.util.ResponseBody;
import com.api.util.RestApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Delete_Put_Test extends TestBase{
	
	private String serviceURL;
	RestApiResponse response;
	
	@BeforeMethod
	public void setUp() {
		serviceURL = prop.getProperty("URL");
	}
	
	
	@Test
	public void validateDeletePut() {
		
		String jsonPost = "";
		String jsonPut = "";
		String urlGet = serviceURL+EndPointURL.GET_BY_ID.getResourcePath("101");
		String urlPost = serviceURL+EndPointURL.POST.getResourcePath();
		String urlput = serviceURL+EndPointURL.PUT.getResourcePath("101");
		String urlDelete = serviceURL+EndPointURL.DELETE.getResourcePath("101");
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		//post
		response = HttpsClient.postWithSSL(urlPost, jsonPost, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED);
		
		//get
		headers.clear();
		headers.put("Accept", "application/json");
		response = HttpsClient.getWithSSL(urlGet, headers);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		
		//put
		headers.clear();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		response = HttpsClient.putWithSSL(urlput, jsonPut, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		
		//get
		headers.clear();
		headers.put("Accept", "application/json");
		response = HttpsClient.getWithSSL(urlGet, headers);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals(body.BrandName, "Series");
		
		//delete
		response = HttpsClient.deleteWithSSL(urlDelete, null);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
		
		//get
		headers.clear();
		headers.put("Accept", "application/json");
		response = HttpsClient.getWithSSL(urlGet, headers);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
		
		
	}

}
