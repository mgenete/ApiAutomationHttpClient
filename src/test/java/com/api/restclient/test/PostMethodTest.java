package com.api.restclient.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.entity.ContentType;
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

public class PostMethodTest extends TestBase{
	
	String serviceURL;
	
	@BeforeMethod
	public void setUp() {
		serviceURL = prop.getProperty("URL");
	}
	
	
	@Test
	public void testPost() throws ClientProtocolException, IOException {
		String url = serviceURL+EndPointURL.POST.getResourcePath();
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		//headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		
		File file = new File("TestData");
		//String jsonBody = "";
		
		RestApiResponse response = RestClient.postRequest(url, file, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response = RestClient.getRequest(serviceURL+EndPointURL.GET_BY_ID.getResourcePath("120"), headers);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals("Apple", body.BrandName);
		Assert.assertEquals("110", body.Id);
		
		
		
	}

}
