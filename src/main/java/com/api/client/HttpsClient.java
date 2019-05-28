package com.api.client;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.api.util.RestApiResponse;



public class HttpsClient {
	
	private static CloseableHttpResponse response;
	
	
	public static RestApiResponse getWithSSL(URI uri, Map<String, String> headers) {
		HttpGet httpGet = new HttpGet(uri);
		if (headers != null) {
			for (String key : headers.keySet()) {
				httpGet.addHeader(key, headers.get(key));
			}
		}
		return performRequest(httpGet);
	}
	
	public static RestApiResponse getWithSSL(String url, Map<String, String> headers) {
		try {
			return getWithSSL(new URI(url), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse postWithSSL(URI uri, Object content, ContentType type, Map<String, String> headers) {
		 HttpUriRequest httpPost = RequestBuilder.post(uri).setEntity(getHttpEntity(content, type)).build();
		 if (headers != null) {
				for (String key : headers.keySet()) {
					httpPost.addHeader(key, headers.get(key));
				}
			}
		 return performRequest(httpPost);
	}
	
	public static RestApiResponse postWithSSL(String url, Object content, ContentType type, Map<String, String> headers) {
		try {
			return postWithSSL(new URI(url), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse putWithSSL(URI uri, Object content, ContentType type, Map<String, String> headers) {
		 HttpUriRequest httpPut = RequestBuilder.put(uri).setEntity(getHttpEntity(content, type)).build();
		 if (headers != null) {
				for (String key : headers.keySet()) {
					httpPut.addHeader(key, headers.get(key));
				}
			}
		 return performRequest(httpPut);
	}
	
	public static RestApiResponse putWithSSL(String url, Object content, ContentType type, Map<String, String> headers) {
		try {
			return putWithSSL(new URI(url), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestApiResponse deleteWithSSL(URI uri, Map<String, String> headers) {
		HttpDelete httpDelete = new HttpDelete(uri);
		if (headers != null) {
			for (String key : headers.keySet()) {
				httpDelete.addHeader(key, headers.get(key));
			}
		}
		return performRequest(httpDelete);
	}
	
	public static RestApiResponse deleteWithSSL(String url, Map<String, String> headers) {
		try {
			return deleteWithSSL(new URI(url), headers);
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
			throw new RuntimeException("Entity Type not found");
	}
	
	public static SSLContext getSSLContext() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy trust = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trust).build();
	}
	
	public static CloseableHttpClient getHttpsClient(SSLContext sslContext) {
		return HttpClientBuilder.create().setSSLContext(sslContext).build();
	}
	
	private static RestApiResponse performRequest(HttpUriRequest method) {
		
		try (CloseableHttpClient client = getHttpsClient(getSSLContext())) {
			response = client.execute(method);
			ResponseHandler<String> body = new BasicResponseHandler();
			return new RestApiResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));
			
		} catch (Exception e) {
			if (e instanceof HttpResponseException)
				return new RestApiResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}
