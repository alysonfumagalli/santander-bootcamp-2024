package me.dio.domain.model;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Data;

@Data
@Entity(name = "tb_authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate birthDate;
}
