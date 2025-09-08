package com.example.bookstore_api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookstore_api.pojo.CreateAuthorRequest;
import com.example.bookstore_api.pojo.CreateAuthorResponse;
import com.example.bookstore_api.pojo.GetAuthorResponse;

public interface AuthorService {

	public CreateAuthorResponse createAuthor(CreateAuthorRequest createAuthorRequest);
	
	public GetAuthorResponse getAuthor(Long id);

	public CreateAuthorResponse updateAuthor(Long id, CreateAuthorRequest updateAuthorRequest);

	public boolean deleteAuthor(Long id);

	public Page<CreateAuthorResponse> getAuthors(String name, String email, Pageable pageable);
}
