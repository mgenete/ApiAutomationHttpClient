package com.api.util;

public class RestApiResponse {
	
	
	private int statusCode;
	private String responseBody;
	
	
	public RestApiResponse(int statusCode, String responseBody) {
		this.statusCode=statusCode;
		this.responseBody=responseBody;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public String getResponseBody() {
		return responseBody;
	}
	
	
	@Override
	public String toString() {
		return String.format("StatusCode: %1s  Body: %2s", this.statusCode, this.responseBody);
	}

}
