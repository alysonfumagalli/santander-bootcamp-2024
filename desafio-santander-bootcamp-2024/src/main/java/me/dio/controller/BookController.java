package me.dio.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import me.dio.controller.dto.BookDto;
import me.dio.domain.model.Book;
import me.dio.service.BookService;

@CrossOrigin
@RestController
@RequestMapping("/books")
@Tag(name = "Books Controller", description = "RESTful API for managing books.")
public record BookController(BookService bookService) {

	@GetMapping
	@Operation(summary = "Get all books", description = "Retrieve a list of all registered books.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful.") })
	public ResponseEntity<List<BookDto>> findAll() {
		List<Book> books = bookService.findAll();
		List<BookDto> booksDto = books.stream().map(BookDto::new).toList();
		return ResponseEntity.ok(booksDto);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a book by ID", description = "Retrieve a specific book based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful."),
			@ApiResponse(responseCode = "404", description = "Book not found") })
	public ResponseEntity<BookDto> findById(@PathVariable("id") Long id) {
		Book book = bookService.findById(id);
		return ResponseEntity.ok(new BookDto(book));
	}

	@PostMapping
	@Operation(summary = "Create a new book", description = "Create a new book and return the created book's data.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Book created successfully."),
			@ApiResponse(responseCode = "422", description = "Invalid book data provided") })
	public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
		Book book = bookService.create(bookDto.toModel());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
				.toUri();
		return ResponseEntity.created(location).body(new BookDto(book));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a book", description = "Update the data of an existing book based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book updated successfully."),
			@ApiResponse(responseCode = "404", description = "Book not found."),
			@ApiResponse(responseCode = "422", description = "Invalid book data provided.") })
	public ResponseEntity<BookDto> update(@PathVariable("id") Long id, @RequestBody BookDto bookDto) {
		Book book = bookService.update(id, bookDto.toModel());
		return ResponseEntity.ok(new BookDto(book));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a book", description = "Delete an existing book based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Book deleted successfully."),
			@ApiResponse(responseCode = "404", description = "Book not found.") })
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
