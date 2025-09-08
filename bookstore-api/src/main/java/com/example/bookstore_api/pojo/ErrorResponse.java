package com.example.bookstore_api.pojo;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {

	private String errorCode;
	
	private String errorMessage;
	
	private String requestURI;
	
	private List<String> Errors;
	
	public ErrorResponse(String errorCode, String errorMessage, 
			String requestURI, List<String> validationErrors){
		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.requestURI = requestURI;
		this.Errors = validationErrors;
	}
}
