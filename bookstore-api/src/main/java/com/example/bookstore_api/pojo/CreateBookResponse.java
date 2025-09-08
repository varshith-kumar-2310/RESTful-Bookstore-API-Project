package com.example.bookstore_api.pojo;

import lombok.Data;

@Data
public class CreateBookResponse {
	
	private Long id;

    private String title;
	
	private String isbn;
	
	private Double price;
	
	private String publishedDate;
	
	private Long authorId;
	
	private String authorName;
	
	
}
