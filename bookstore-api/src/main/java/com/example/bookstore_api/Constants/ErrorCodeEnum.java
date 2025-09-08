package com.example.bookstore_api.Constants;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	INVALID_REQUEST("1000", "Invalid Request Body"),
	RESOURCE_NOT_FOUND("1001", "Resource not found"),
	DATA_INTEGRITY_VIOLATION("1002", "Data Integrity Violation"),
	INTERNAL_SERVER_ERROR("2000", "Internal server Error");
	
	private final String errorCode;
    private final String errorMessage;

    ErrorCodeEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
