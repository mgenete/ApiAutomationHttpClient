package com.api.client;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import com.api.util.RestApiResponse;

public class RestClient {
	
	
	
	private static CloseableHttpResponse response;
	
	
	public static RestApiResponse getRequest(URI uri, Map<String, String> headers) {
		HttpGet httpGet = new HttpGet(uri);
		if(headers != null) {
			httpGet.setHeaders(getHeaders(headers));
		}
		return performRequest(httpGet);
	}
	
	public static RestApiResponse getRequest(String uri, Map<String, String> headers) {
		try {
			return getRequest(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse postRequest(URI uri, Object content, ContentType type, Map<String, String> headers) {
		HttpPost httpPost = new HttpPost(uri);
		if(headers != null) {
			httpPost.setHeaders(getHeaders(headers));
		}
		httpPost.setEntity(getHttpEntity(content, type));
		return performRequest(httpPost);
	}
	
	public static RestApiResponse postRequest(String uri, Object content, ContentType type, Map<String, String> headers) {
		try {
			return postRequest(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse deleteRequest(URI uri, Map<String, String> headers) {
		HttpDelete httpDelete = new HttpDelete(uri);
		if(headers != null) {
			httpDelete.setHeaders(getHeaders(headers));
		}
		return performRequest(httpDelete);
	}
	
	public static RestApiResponse deleteRequest(String uri, Map<String, String> headers) {
		try {
			return deleteRequest(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse putRequest(URI uri, Object content, ContentType type, Map<String, String> headers) {
		HttpPut httpPut = new HttpPut(uri);
		if(headers != null) {
			httpPut.setHeaders(getHeaders(headers));
		}
		httpPut.setEntity(getHttpEntity(content, type));
		return performRequest(httpPut);
	}
	
	public static RestApiResponse putRequest(String uri, Object content, ContentType type, Map<String, String> headers) {
		try {
			return putRequest(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	private static HttpEntity getHttpEntity(Object content, ContentType type) {
		if(content instanceof String)
			return new StringEntity((String)content, type);
		else if(content instanceof File)
			return new FileEntity((File)content, type);
		else
			throw new RuntimeException("Entity type not found");
	}
		
	private static RestApiResponse performRequest(HttpUriRequest method) {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()){
			response = client.execute(method);
			ResponseHandler<String> body = new BasicResponseHandler();
			return new RestApiResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));
			
			
		} catch (Exception e) {
			if(e instanceof HttpResponseException)
				return new RestApiResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}
		
	private static Header[] getHeaders(Map<String, String> headers) {
		Header[] customHeaders = new Header[headers.size()];
		int i=0;
		for(String key: headers.keySet()) {
			customHeaders[i++] = new BasicHeader(key, headers.get(key));
		}
		return customHeaders;
	}
	

}
