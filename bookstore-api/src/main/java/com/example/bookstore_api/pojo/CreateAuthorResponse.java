package com.example.bookstore_api.pojo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CreateAuthorResponse {

	private Long authorId;
	
	private String name;
	
	private String email;
	
	private String bio;
	
}
