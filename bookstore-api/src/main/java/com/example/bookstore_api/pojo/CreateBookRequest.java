package com.example.bookstore_api.pojo;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateBookRequest {

	@NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title must be at most 200 characters")
	private String title;
	
	@NotBlank(message = "ISBN is required")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
	private String isbn;
	
	@NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
	private Double price;
	
	@Nullable
	private String publishedDate;
	
	@NotNull(message = "Author ID is required")
	private Long authorId;
	
}
