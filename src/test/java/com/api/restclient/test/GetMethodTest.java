package com.api.restclient.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.base.TestBase;
import com.api.client.RestClient;
import com.api.util.EndPointURL;
import com.api.util.ResponseBody;
import com.api.util.RestApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetMethodTest extends TestBase{
	
	private String serviceURL;
	
	@BeforeMethod
	public void setUp() {
		serviceURL = prop.getProperty("URL");
	}
	
	
	@Test
	public void validateGetAll() {
		String url = serviceURL+EndPointURL.GET_ALL.getResourcePath();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		//headers.put("Authorization", "Bearer e14160e3e2b9345daef07673093e14bcc5f207fe");
		
		RestApiResponse response = RestClient.getRequest(url, headers);
		Assert.assertTrue((HttpStatus.SC_OK == response.getStatusCode()) || (HttpStatus.SC_NO_CONTENT == response.getStatusCode()), "Expected status code not found");
		
	}
	
	@Test
	public void validateGetById() {
		String url = serviceURL+EndPointURL.GET_BY_ID.getResourcePath("110");
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		
		RestApiResponse response = RestClient.getRequest(url, headers);
		Assert.assertTrue((HttpStatus.SC_OK == response.getStatusCode()) || (HttpStatus.SC_NO_CONTENT == response.getStatusCode()), "Expected status code not found");
		
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		
		Assert.assertEquals("Apple", body.BrandName);
		Assert.assertEquals("110", body.Id);
		Assert.assertEquals("MacBook Air", body.LaptopName);
		Assert.assertEquals("8GB RAM", body.Features.Feature.get(0));
	}

}
