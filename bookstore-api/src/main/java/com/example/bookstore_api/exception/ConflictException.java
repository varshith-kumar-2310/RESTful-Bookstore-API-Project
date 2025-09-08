package com.example.bookstore_api.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ConflictException extends RuntimeException {
	
	public ConflictException(String message) {
        super(message);
    }
	
}
