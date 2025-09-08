package com.example.bookstore_api.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.bookstore_api.pojo.CreateBookRequest;
import com.example.bookstore_api.pojo.CreateBookResponse;
import com.example.bookstore_api.pojo.GetBookResponse;
import com.example.bookstore_api.pojo.UpdateBookRequest;

public interface BookService {

	public CreateBookResponse createBook(CreateBookRequest createBookRequest);
	
	public GetBookResponse getBook(Long id);
	
	public CreateBookResponse updateBook(Long id, UpdateBookRequest updateBookRequest);

	public boolean deleteBook(Long id);

	public Page<GetBookResponse> getBooks(String title, Long authorId, Double minPrice, Double maxPrice,
			String publishedAfter, Pageable pageable);
}
