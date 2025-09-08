package com.example.bookstore_api.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.bookstore_api.Constants.ErrorCodeEnum;
import com.example.bookstore_api.pojo.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponse error = new ErrorResponse(
                ErrorCodeEnum.INVALID_REQUEST.getErrorCode(),
                ErrorCodeEnum.INVALID_REQUEST.getErrorMessage(),
                request.getRequestURI(),
                validationErrors
        );
        
        log.info("handleValidationErrors : {}", error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        List<String> ResourceNotFoundErrors = new ArrayList<>();
        ResourceNotFoundErrors.add(ex.getMessage());
        ErrorResponse error = new ErrorResponse(
                ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorCode(),
                ErrorCodeEnum.RESOURCE_NOT_FOUND.getErrorMessage(), 
                request.getRequestURI(),
                ResourceNotFoundErrors
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
	        DataIntegrityViolationException ex,
	        HttpServletRequest request) {

	    List<String> errors = new ArrayList<>();

	    // Check if it's caused by ISBN unique constraint
	    String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
	    if (rootMsg != null && rootMsg.toLowerCase().contains("isbn")) {
	        errors.add("ISBN must be unique. A book with this ISBN already exists.");
	    }
	    else if (rootMsg != null && rootMsg.toLowerCase().contains("email")) {
	        errors.add("Email must be unique. An author with this Email already exists.");
	    }else {
	        errors.add("Data integrity violation: " + rootMsg);
	    }

	    ErrorResponse error = new ErrorResponse(
	            ErrorCodeEnum.DATA_INTEGRITY_VIOLATION.getErrorCode(),
	            ErrorCodeEnum.DATA_INTEGRITY_VIOLATION.getErrorMessage(),
	            request.getRequestURI(),
	            errors
	    );

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT); // 409 Conflict
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
	    List<String> errors = new ArrayList<>();
	    errors.add(ex.getMessage() != null ? ex.getMessage() : "Unexpected error occurred");

	    ErrorResponse error = new ErrorResponse(
	            ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorCode(),
	            ErrorCodeEnum.INTERNAL_SERVER_ERROR.getErrorMessage(),
	            request.getRequestURI(),
	            errors
	    );

	    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
