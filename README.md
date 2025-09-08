# RESTful Bookstore API ![Java](https://img.shields.io/badge/Java-17-blue) ![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.5-green) ![H2 Database](https://img.shields.io/badge/H2-Database-orange)

## Table of Contents
- [Introduction](#introduction)  
- [Abstract](#abstract)  
- [Tools Used](#tools-used)  
- [Project Structure & Steps](#project-structure--steps)  
- [Features](#features)  
- [Getting Started](#getting-started)  
- [API Endpoints & Example Requests](#api-endpoints--example-requests)  
  - [Authors](#authors)  
  - [Books](#books)  
- [Swagger UI](#swagger-ui)  
- [Curl Examples](#curl-examples)
- [Project Report](#project-report)
- [Conclusion](#conclusion)  

---

## Introduction
The **RESTful Bookstore API** is a backend application to manage books and authors in a bookstore. It supports **CRUD operations** with advanced features like **filtering, pagination, and sorting**. **Swagger UI** is integrated for API documentation and interactive testing.

---

## Abstract
This project demonstrates the design and implementation of a **Spring Boot RESTful API** with clean separation between persistence and API layers. Key features include:

- Request/Response POJOs mapped to entity classes using **ModelMapper**  
- **H2 in-memory database** for quick testing  
- Centralized **exception handling** with custom exceptions and error codes  
- **Swagger UI** integration for interactive API exploration  

---

## Tools Used
- **Java 17**  
- **Spring Boot 3.5.5**  
- **Spring Data JPA & Hibernate**  
- **H2 Database**  
- **Springdoc OpenAPI / Swagger UI**  
- **ModelMapper**  
- **Lombok**  
- **Eclipse STS**  
- **Maven**  

---

## Project Structure & Steps
1. **Project Setup:** Maven-based Spring Boot project.  
2. **Entity Design:** `BookEntity` and `AuthorEntity`.  
3. **POJO Layer:** Request and response POJOs in `.pojo` package.  
4. **Model Mapping:** Using **ModelMapper** for automatic conversion.  
5. **Repository Layer:** Spring Data JPA repositories.  
6. **Service Layer:** Business logic for books/authors.  
7. **Controller Layer:** REST endpoints (`/books`, `/authors`).  
8. **Validation:** Input validation using annotations.  
9. **Filtering, Pagination & Sorting:** Dynamic queries with Spring Data JPA.  
10. **Database Integration:** **H2 in-memory DB**.  
11. **Exception Handling:** Centralized using `@ControllerAdvice`.  
12. **Error Codes & Enums:** `ErrorCodeEnum` for standardized responses.  
13. **API Documentation:** Springdoc OpenAPI, Swagger UI at `/swagger-ui/index.html`.  
14. **Testing:** Spring Boot testing for repositories, services, and controllers.  

---

## Features
- CRUD operations for **Books** and **Authors**  
- Input validation with meaningful error messages  
- **Filtering, pagination, and sorting**  
- Centralized **exception handling** with error codes  
- Interactive **Swagger UI**  
- H2 in-memory database for rapid development/testing  

---

## Getting Started

### Prerequisites
- **Java 17**  
- **Maven**  
- IDE (Eclipse STS or IntelliJ IDEA)  

### Run the Application
```bash
git clone <repository-url>
cd bookstore-api
mvn spring-boot:run
Access Swagger UI:

bash
Copy code
http://localhost:8080/swagger-ui/index.html
API Endpoints & Example Requests
Authors
Create Author

http
Copy code
POST /authors
Content-Type: application/json

{
  "name": "J.K. Rowling",
  "email": "jk.rowling@example.com",
  "bio": "write bio here.."
}
Get Author by ID

http
Copy code
GET /authors/{id}
Update Author

http
Copy code
PUT /authors/{id}
Content-Type: application/json

{
  "name": "Joanne Rowling",
  "email": "joanne.rowling@example.com",
  "bio": "update bio here.."
}
Delete Author

http
Copy code
DELETE /authors/{id}
Get All Authors with Pagination

http
Copy code
GET /authors?page=0&size=10&sort=name,asc
Books
Create Book

http
Copy code
POST /books
Content-Type: application/json

{
  "title": "Harry Potter and the Sorcerer's Stone",
  "isbn": "978-0439708180",
  "price": 100.50,
  "authorId": 1,
  "publishedDate": "1997-06-26"
}
Get Book by ID

http
Copy code
GET /books/{id}
Update Book

http
Copy code
PUT /books/{id}
Content-Type: application/json

{
  "title": "Harry Potter and the Philosopher's Stone",
  "isbn": "978-0439708180",
  "price": 100.50,
  "publishedDate": "1997-06-26"
}
Delete Book

http
Copy code
DELETE /books/{id}
Get All Books with Filtering

http
Copy code
GET /books?title=Harry&author=Rowling&page=0&size=10&sort=title,asc
Swagger UI
You can explore all endpoints interactively using Swagger UI:

bash
Copy code
http://localhost:8080/swagger-ui/index.html
Example screenshots (optional in repo images folder):

List all authors

Create new book

Update author details

Add screenshots in /docs or /images folder and link like ![List Authors](images/list-authors.png)

Curl Examples
Create Author

bash
Copy code
curl -X POST "http://localhost:8080/authors" \
-H "Content-Type: application/json" \
-d '{"name":"J.K. Rowling","email":"jk.rowling@example.com"}'
Get Book by ID

bash
Copy code
curl -X GET "http://localhost:8080/books/1"
Update Book

bash
Copy code
curl -X PUT "http://localhost:8080/books/1" \
-H "Content-Type: application/json" \
-d '{"title":"Harry Potter and the Philosopher\'s Stone","isbn":"978-0439708180","authorId":1,"publishedDate":"1997-06-26"}'
Delete Author

bash
Copy code
curl -X DELETE "http://localhost:8080/authors/1"
Conclusion
The RESTful Bookstore API provides a robust, maintainable backend using Spring Boot. It supports filtering, pagination, sorting, and centralized exception handling, with Swagger UI making testing and documentation effortless. This foundation can be extended with external database support, authentication, and deployment.

## Project Report

You can download or view the complete project report [here](./RESTful Bookstore API.pdf).

