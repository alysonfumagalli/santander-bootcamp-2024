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

import me.dio.controller.dto.AuthorDto;
import me.dio.domain.model.Author;
import me.dio.service.AuthorService;

@CrossOrigin
@RestController
@RequestMapping("/authors")
@Tag(name = "Authors Controller", description = "RESTful API for managing authors.")
public record AuthorController(AuthorService authorService) {

	@GetMapping
	@Operation(summary = "Get all authors", description = "Retrieve a list of all registered authors.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful.") })
	public ResponseEntity<List<AuthorDto>> findAll() {
		List<Author> authors = authorService.findAll();
		List<AuthorDto> authorsDto = authors.stream().map(AuthorDto::new).toList();
		return ResponseEntity.ok(authorsDto);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get an author by ID", description = "Retrieve a specific author based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful."),
			@ApiResponse(responseCode = "404", description = "Author not found") })
	public ResponseEntity<AuthorDto> findById(@PathVariable("id") Long id) {
		Author author = authorService.findById(id);
		return ResponseEntity.ok(new AuthorDto(author));
	}

	@PostMapping
	@Operation(summary = "Create a new author", description = "Create a new author and return the created author's data.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Author created successfully."),
			@ApiResponse(responseCode = "422", description = "Invalid author data provided") })
	public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {
		Author author = authorService.create(authorDto.toModel());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId())
				.toUri();
		return ResponseEntity.created(location).body(new AuthorDto(author));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an author", description = "Update the data of an existing author based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Author updated successfully."),
			@ApiResponse(responseCode = "404", description = "Author not found."),
			@ApiResponse(responseCode = "422", description = "Invalid author data provided.") })
	public ResponseEntity<AuthorDto> update(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto) {
		Author author = authorService.update(id, authorDto.toModel());
		return ResponseEntity.ok(new AuthorDto(author));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an author", description = "Delete an existing author based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Author deleted successfully."),
			@ApiResponse(responseCode = "404", description = "Author not found.") })
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		authorService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
