package com.example.bookstore_api.pojo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateAuthorRequest {

	@NotBlank(message = "Author name is required")
    @Size(max = 100, message = "Author name must be at most 100 characters")
	private String name;
	
	@NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
	private String email;
	
	@Size(max = 255, message = "Bio must be at most 255 characters")
	private String bio;
	
}
