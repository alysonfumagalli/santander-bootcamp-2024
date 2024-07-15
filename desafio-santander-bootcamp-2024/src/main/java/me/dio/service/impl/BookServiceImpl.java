package me.dio.service.impl;

import static java.util.Optional.ofNullable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.domain.model.Book;
import me.dio.domain.repository.BookRepository;
import me.dio.service.BookService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookRepository bookRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Book> findAll() {
		return bookRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Book findById(Long id) {
		return bookRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Transactional
	@Override
	public Book create(Book bookToCreate) {
		ofNullable(bookToCreate).orElseThrow(() -> new BusinessException("Book to create must not be null."));
		ofNullable(bookToCreate.getAuthor()).orElseThrow(() -> new BusinessException("Book author must not be null."));
		ofNullable(bookToCreate.getPublishingHouse()).orElseThrow(() -> new BusinessException("Book publishing house must not be null."));
		return bookRepository.save(bookToCreate);
	}

	@Override
	public Book update(Long id, Book bookToUpdate) {
		Book dbBook = findById(id);
		if (!dbBook.getId().equals(bookToUpdate.getId()))
			throw new BusinessException("Update IDs must be the same.");
		
		dbBook.setAuthor(bookToUpdate.getAuthor());
		dbBook.setISBN(bookToUpdate.getISBN());
		dbBook.setPublishedDate(bookToUpdate.getPublishedDate());
		dbBook.setPublishingHouse(bookToUpdate.getPublishingHouse());
		dbBook.setTitle(bookToUpdate.getTitle());
		
		return bookRepository.save(dbBook);
	}

	@Override
	public void delete(Long id) {
		Book dbBook = findById(id);
		bookRepository.delete(dbBook);
	}

}
