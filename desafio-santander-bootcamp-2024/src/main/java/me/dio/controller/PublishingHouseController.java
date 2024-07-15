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

import me.dio.controller.dto.PublishingHouseDto;
import me.dio.domain.model.PublishingHouse;
import me.dio.service.PublishingHouseService;

@CrossOrigin
@RestController
@RequestMapping("/publishing-houses")
@Tag(name = "Publishing Houses Controller", description = "RESTful API for managing publishing houses.")
public record PublishingHouseController(PublishingHouseService publishingHouseService) {

	@GetMapping
	@Operation(summary = "Get all publishing houses", description = "Retrieve a list of all registered publishing houses.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful.") })
	public ResponseEntity<List<PublishingHouseDto>> findAll() {
		List<PublishingHouse> publishingHouses = publishingHouseService.findAll();
		List<PublishingHouseDto> publishingHousesDto = publishingHouses.stream().map(PublishingHouseDto::new).toList();
		return ResponseEntity.ok(publishingHousesDto);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a publishing house by ID", description = "Retrieve a specific publishing house based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Operation successful."),
			@ApiResponse(responseCode = "404", description = "Publishing house not found") })
	public ResponseEntity<PublishingHouseDto> findById(@PathVariable("id") Long id) {
		PublishingHouse publishingHouse = publishingHouseService.findById(id);
		return ResponseEntity.ok(new PublishingHouseDto(publishingHouse));
	}

	@PostMapping
	@Operation(summary = "Create a new publishing house", description = "Create a new publishing house and return the created author's data.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Publishing house created successfully."),
			@ApiResponse(responseCode = "422", description = "Invalid publishing house data provided") })
	public ResponseEntity<PublishingHouseDto> create(@RequestBody PublishingHouseDto publishingHouseDto) {
		PublishingHouse publishingHouse = publishingHouseService.create(publishingHouseDto.toModel());
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(publishingHouse.getId())
				.toUri();
		return ResponseEntity.created(location).body(new PublishingHouseDto(publishingHouse));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a publishing house", description = "Update the data of an existing publishing house based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Publishing house updated successfully."),
			@ApiResponse(responseCode = "404", description = "Publishing house not found."),
			@ApiResponse(responseCode = "422", description = "Invalid publishing house data provided.") })
	public ResponseEntity<PublishingHouseDto> update(@PathVariable("id") Long id, @RequestBody PublishingHouseDto publishingHouseDto) {
		PublishingHouse publishingHouse = publishingHouseService.update(id, publishingHouseDto.toModel());
		return ResponseEntity.ok(new PublishingHouseDto(publishingHouse));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a publishing house", description = "Delete an existing publishing house based on its ID.")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Publishing house deleted successfully."),
			@ApiResponse(responseCode = "404", description = "Publishing house not found.") })
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		publishingHouseService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
