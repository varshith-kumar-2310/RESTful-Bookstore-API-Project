package com.example.bookstore_api.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException(String message) {
        super(message);
    }
	
}
