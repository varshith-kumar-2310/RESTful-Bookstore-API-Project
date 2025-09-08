package com.example.bookstore_api.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "authors")
@EntityListeners(AuditingEntityListener.class)  //This activates auditing
@Data
public class AuthorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//assigns a auto-generated value to author id
	private Long AuthorId;
	
	@Column(name = "AuthorName",nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	
	@Column(nullable = false, length = 200)
	private String bio;
	
	@CreatedDate
    @Column(updatable = false, nullable = false)  // never updated after insert
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
	
	
}
