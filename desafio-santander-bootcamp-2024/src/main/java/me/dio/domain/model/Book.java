package me.dio.domain.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity(name = "tb_books")
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	@Column(unique = true)
	private String ISBN;
	private LocalDate publishedDate;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Author author;
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private PublishingHouse publishingHouse;
}
