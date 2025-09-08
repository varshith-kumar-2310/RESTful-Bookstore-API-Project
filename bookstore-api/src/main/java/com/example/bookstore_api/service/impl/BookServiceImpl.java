package com.example.bookstore_api.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore_api.entity.AuthorEntity;
import com.example.bookstore_api.entity.BookEntity;
import com.example.bookstore_api.exception.ConflictException;
import com.example.bookstore_api.exception.ResourceNotFoundException;
import com.example.bookstore_api.pojo.CreateBookRequest;
import com.example.bookstore_api.pojo.CreateBookResponse;
import com.example.bookstore_api.pojo.GetBookResponse;
import com.example.bookstore_api.pojo.UpdateBookRequest;
import com.example.bookstore_api.repo.AuthorRepository;
import com.example.bookstore_api.repo.BookRepository;
import com.example.bookstore_api.service.interfaces.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final ModelMapper modelMapper;
	
	private final BookRepository bookRepository;
	
	private final AuthorRepository authorRepository;
	
	@Override
	public CreateBookResponse createBook(CreateBookRequest createBookRequest) {
		
		log.info("createBookRequest : {}", createBookRequest);
		
		//convert CreateAuthorRequest POJO to entity
		BookEntity bookEntity = modelMapper.map(createBookRequest, BookEntity.class);
		log.info("Converted POJO -> Entity | bookEntity : {}",bookEntity);
		
		bookEntity.setId(null);
		log.info("Converted to Entity  and id->null| bookEntity : {}",bookEntity);
		
		AuthorEntity author = authorRepository.findById(createBookRequest.getAuthorId())
		        .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + createBookRequest.getAuthorId()));

		bookEntity.setAuthor(author);
		log.info("Converted POJO -> Entity after setting AuthorEntity | bookEntity : {}",bookEntity);
				
	    //save to DB and return it
	    //save() saves the entity provided to DB and returns the saved entity
		BookEntity savedBookEntity = bookRepository.save(bookEntity);
		log.info("Saved Bookentity | savedBookEntity : {}",savedBookEntity);
				
		CreateBookResponse response = modelMapper.map(savedBookEntity, CreateBookResponse.class);
		log.info("Converted Entity -> POJO | createResponse : {}",response);
				
		return response;
		
	}

	@Override
	public GetBookResponse getBook(Long id) {
		
		log.info("Book Id : {}",id);
		
		BookEntity retrievedBookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		log.info("retrieved entity | retrievedBookEntity : {}",retrievedBookEntity);
		
		GetBookResponse response = modelMapper.map(retrievedBookEntity, GetBookResponse.class);
		log.info("Converted Entity -> POJO | getResponse  Book: {}",response);
		
		return response;
		
	}

	@Override
	public CreateBookResponse updateBook(Long id, UpdateBookRequest updateBookRequest) {
		
		log.info("Book Id : {}",id);
		
		BookEntity retrievedBookEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
		log.info("retrieved entity | retrievedBookEntity : {}",retrievedBookEntity);
		
		retrievedBookEntity.setTitle(updateBookRequest.getTitle());
		retrievedBookEntity.setIsbn(updateBookRequest.getIsbn());
		retrievedBookEntity.setPrice(updateBookRequest.getPrice());
		retrievedBookEntity.setPublishedDate(updateBookRequest.getPublishedDate());
		retrievedBookEntity.setUpdatedAt(LocalDateTime.now());
		
		log.info("updated retrieved entity | retrievedAuthorEntity : {}",retrievedBookEntity);
		
		BookEntity updatedBookEntity = bookRepository.save(retrievedBookEntity);
		log.info("Saved entity | updatedAuthorEntity : {}",updatedBookEntity);
		
		CreateBookResponse response = modelMapper.map(updatedBookEntity, CreateBookResponse.class);
		log.info("Converted Entity -> POJO | getResponse : {}",response);
		
		return response;
		
	}

	@Override
	public boolean deleteBook(Long id) {
		
		log.info("Book Id : {}", id);
		
		if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true; // deletion successful
        } else {
        		if (!bookRepository.existsById(id)){// author with provided id not found
        			//404-> notFound error
        			throw new ResourceNotFoundException("Book not found with id: " + id);
        		}
        		else {
        			
        			throw new ConflictException("Book with id: " + id + " exists but operation conflicts with its current state");
        		}
        		
        }
		
	}

	@Override
	public Page<GetBookResponse> getBooks(String title, Long authorId, Double minPrice, Double maxPrice,
			String publishedAfter, Pageable pageable) {
		    
		// Build dynamic filtering using Specification or custom query
		Specification<BookEntity> spec = Specification.where(null);

		if (title != null && !title.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
		}
		if (authorId != null) {
			spec = spec.and((root, query, cb) -> cb.equal(root.get("author").get("id"), authorId));
		}
		if (minPrice != null) {
			spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("price"), minPrice));
		}
		if (maxPrice != null) {
			spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("price"), maxPrice));
		}
		if (publishedAfter != null) {
			LocalDate date = LocalDate.parse(publishedAfter);
			spec = spec.and((root, query, cb) -> cb.greaterThan(root.get("publishedDate"), date));
		}

		// Fetch data with pagination + sorting
		Page<BookEntity> bookPage = bookRepository.findAll(spec, pageable);
		log.info("bookPage : {}", publishedAfter);

		//Convert to response DTO
		return bookPage.map(book -> modelMapper.map(book, GetBookResponse.class));
	}
}
