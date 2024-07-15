package me.dio.domain.model;

import jakarta.persistence.*;

import java.time.LocalDate;

import lombok.Data;

@Data
@Entity(name = "tb_publishing_houses")
public class PublishingHouse {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phone;
	private LocalDate foundationDate;
}
