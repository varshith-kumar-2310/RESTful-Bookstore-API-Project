package com.example.bookstore_api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.example.bookstore_api.pojo.CreateAuthorRequest;
import com.example.bookstore_api.pojo.CreateAuthorResponse;
import com.example.bookstore_api.pojo.GetAuthorResponse;
import com.example.bookstore_api.service.interfaces.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(Constants.AUTHORS)
@Slf4j
@RequiredArgsConstructor
public class AuthorController {
	
	private final AuthorService authorService;
	
	@PostMapping
	public ResponseEntity<CreateAuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest createAuthorRequest){
		log.info("Recieved createAuthorRequest : {}", createAuthorRequest);
		
		CreateAuthorResponse response = authorService.createAuthor(createAuthorRequest);
		log.info("Response From Service | response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(response);
	}
	
	@GetMapping(Constants.ID)
	public ResponseEntity<GetAuthorResponse> getAuthor(@PathVariable Long id){
		log.info("Recieved AuthorId | id : {}", id);
		
		GetAuthorResponse response = authorService.getAuthor(id);
		log.info("Response From Service | response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
	
	@GetMapping
    public Page<CreateAuthorResponse> getAuthors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable
    ) {
        return authorService.getAuthors(name, email, pageable);
    }
	
	@PutMapping(Constants.ID)
	public ResponseEntity<CreateAuthorResponse> updateAuthor(@PathVariable Long id,
			@RequestBody CreateAuthorRequest updateAuthorRequest){
		log.info("Recieved AuthorId | id : {}", id);
		log.info("Recieved updateAuthorRequest : {}", updateAuthorRequest);
		
		CreateAuthorResponse response = authorService.updateAuthor(id, updateAuthorRequest);
		log.info("Response From Service | response : {}",response);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(response);
	}
	
	@DeleteMapping(Constants.ID)
	public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
		authorService.deleteAuthor(id);
	    return ResponseEntity.noContent().build();
	}
	

}
