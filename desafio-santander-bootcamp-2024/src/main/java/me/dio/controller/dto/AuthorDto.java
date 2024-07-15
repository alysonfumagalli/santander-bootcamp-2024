package me.dio.controller.dto;

import java.time.LocalDate;

import me.dio.domain.model.Author;

public record AuthorDto(Long id, String name, LocalDate birthDate) {

    public AuthorDto(Author model) {
        this(model.getId(), model.getName(), model.getBirthDate());
    }

    public Author toModel() {
        Author model = new Author();
        model.setId(this.id);
        model.setName(this.name);
        model.setBirthDate(this.birthDate);
        return model;
    }
}