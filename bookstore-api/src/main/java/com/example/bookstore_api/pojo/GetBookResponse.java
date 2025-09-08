package com.example.bookstore_api.pojo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class GetBookResponse {
	
	private Long id;

    private String title;
	
	private String isbn;
	
	private Double price;
	
	private String publishedDate;
	
	private Long authorId;
	
	private String authorName;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	
}
