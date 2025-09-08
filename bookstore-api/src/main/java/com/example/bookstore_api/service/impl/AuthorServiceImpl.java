package com.example.bookstore_api.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.bookstore_api.entity.AuthorEntity;
import com.example.bookstore_api.exception.ConflictException;
import com.example.bookstore_api.exception.ResourceNotFoundException;
import com.example.bookstore_api.pojo.CreateAuthorRequest;
import com.example.bookstore_api.pojo.CreateAuthorResponse;
import com.example.bookstore_api.pojo.GetAuthorResponse;
import com.example.bookstore_api.repo.AuthorRepository;
import com.example.bookstore_api.service.interfaces.AuthorService;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

	private final ModelMapper modelMapper;
	
	private final AuthorRepository authorRepository;
	
	@Override
	public CreateAuthorResponse createAuthor(CreateAuthorRequest createRequest) {
		
		log.info("createRequest : {}",createRequest);
		
		//convert CreateAuthorRequest POJO to entity
		AuthorEntity authorEntity = modelMapper.map(createRequest, AuthorEntity.class);
		log.info("Converted POJO -> Entity | authorEntity : {}",authorEntity);
		
		//save to DB and return it
		//save() saves the entity provided to DB and returns the saved entity
		AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
		log.info("Saved entity | savedAuthorEntity : {}",savedAuthorEntity);
		
		CreateAuthorResponse response = modelMapper.map(savedAuthorEntity, CreateAuthorResponse.class);
		log.info("Converted Entity -> POJO | createResponse : {}",response);
		
		return response;
	}
	
	@Override
	public GetAuthorResponse getAuthor(Long id) {
		
		log.info("Author Id : {}",id);
		
		AuthorEntity retrievedAuthorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
		log.info("retrieved entity | retrievedAuthorEntity : {}",retrievedAuthorEntity);
		
		GetAuthorResponse response = modelMapper.map(retrievedAuthorEntity, GetAuthorResponse.class);
		log.info("Converted Entity -> POJO | getResponse : {}",response);
		
		return response;
	}
	
	@Override
	public CreateAuthorResponse updateAuthor(Long id, 
			CreateAuthorRequest updateAuthorRequest) {
		
		log.info("Author Id : {}",id);
		
		AuthorEntity retrievedAuthorEntity = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
		log.info("retrieved entity | retrievedAuthorEntity : {}",retrievedAuthorEntity);
		
		retrievedAuthorEntity.setName(updateAuthorRequest.getName());
		retrievedAuthorEntity.setEmail(updateAuthorRequest.getEmail());
		retrievedAuthorEntity.setBio(updateAuthorRequest.getBio());
		retrievedAuthorEntity.setUpdatedAt(LocalDateTime.now());
		
		log.info("updated retrieved entity | retrievedAuthorEntity : {}",retrievedAuthorEntity);
		
		AuthorEntity updatedAuthorEntity = authorRepository.save(retrievedAuthorEntity);
		log.info("Saved entity | updatedAuthorEntity : {}",updatedAuthorEntity);
		
		CreateAuthorResponse response = modelMapper.map(updatedAuthorEntity, CreateAuthorResponse.class);
		log.info("Converted Entity -> POJO | getResponse : {}",response);
		
		return response;
	}
	
	@Override
	public boolean deleteAuthor(Long id) {
		
		log.info("Author Id : {}", id);
		
		if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true; // deletion successful
        } else {
	        	if (!authorRepository.existsById(id)){// book with provided id not found
	    			//404-> notFound error
	    			throw new ResourceNotFoundException("Author not found with id: " + id);
	    		}
	    		else {
	    			
	    			throw new ConflictException("Author with id: " + id + " exists but operation conflicts with its current state");
	    		}
        }
	}

	@Override
	public Page<CreateAuthorResponse> getAuthors(String name, String email, Pageable pageable) {
		
		Specification<AuthorEntity> spec = (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            
            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
            }

            return predicate;
        };

        return authorRepository.findAll(spec, pageable)
                .map(author -> modelMapper.map(author, CreateAuthorResponse.class));
		
	}

}
