package me.dio.controller.dto;

import java.time.LocalDate;

import me.dio.domain.model.Author;
import me.dio.domain.model.Book;
import me.dio.domain.model.PublishingHouse;

public record BookDto(Long id, String title, String ISBN, LocalDate publishedDate, Author author, PublishingHouse publishingHouse) {

    public BookDto(Book model) {
        this(model.getId(), model.getTitle(), model.getISBN(), model.getPublishedDate(), model.getAuthor(), model.getPublishingHouse());
    }

    public Book toModel() {
        Book model = new Book();
        model.setId(this.id);
        model.setTitle(this.title);
        model.setISBN(this.ISBN);
        model.setPublishedDate(this.publishedDate);
        model.setAuthor(this.author);
        model.setPublishingHouse(this.publishingHouse);
        return model;
    }
}