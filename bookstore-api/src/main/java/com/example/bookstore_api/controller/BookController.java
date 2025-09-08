package com.example.bookstore_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore_api.Constants.Constants;
import com.example.bookstore_api.pojo.CreateBookRequest;
import com.example.bookstore_api.pojo.CreateBookResponse;
import com.example.bookstore_api.pojo.GetBookResponse;
import com.example.bookstore_api.pojo.UpdateBookRequest;
import com.example.bookstore_api.service.interfaces.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.BOOKS)
@Slf4j
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	
	@PostMapping
	public ResponseEntity<CreateBookResponse> createBook(@Valid @RequestBody CreateBookRequest createBookRequest){
		log.info("Recieved createAuthorRequest : {}", createBookRequest);
		
		CreateBookResponse response = bookService.createBook(createBookRequest);
		log.info("Response From Service | response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping(Constants.ID)
	public ResponseEntity<GetBookResponse> getBook(@PathVariable Long id){
		log.info("Recieved BookId | id : {}", id);
		
		GetBookResponse response = bookService.getBook(id);
		log.info("Response From Service | Book get response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
	
	@GetMapping
	public Page<GetBookResponse> getBooks(
	        @RequestParam(required = false) String title,
	        @RequestParam(required = false) Long authorId,
	        @RequestParam(required = false) Double minPrice,
	        @RequestParam(required = false) Double maxPrice,
	        @RequestParam(required = false) String publishedAfter,
	        @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

	    return bookService.getBooks(title, authorId, minPrice, maxPrice, publishedAfter, pageable);
	}
	
	@PutMapping(Constants.ID)
	public ResponseEntity<CreateBookResponse> updateBook(@PathVariable Long id,
			@RequestBody UpdateBookRequest updateBookRequest){
		log.info("Recieved AuthorId | id : {}", id);
		log.info("Recieved updateAuthorRequest : {}", updateBookRequest);
		
		CreateBookResponse response = bookService.updateBook(id, updateBookRequest);
		log.info("Response From Service | response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
	
	@DeleteMapping(Constants.ID)
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
	    bookService.deleteBook(id);
	    return ResponseEntity.noContent().build();
	}
	

}
