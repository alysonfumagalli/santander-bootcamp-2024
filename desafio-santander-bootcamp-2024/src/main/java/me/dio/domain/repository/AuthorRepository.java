package me.dio.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.dio.domain.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {}