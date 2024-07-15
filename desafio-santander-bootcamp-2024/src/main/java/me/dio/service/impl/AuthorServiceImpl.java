package me.dio.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.dio.domain.model.Author;
import me.dio.domain.repository.AuthorRepository;
import me.dio.service.AuthorService;
import me.dio.service.exception.BusinessException;
import me.dio.service.exception.NotFoundException;

import static java.util.Optional.ofNullable;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	private AuthorRepository authorRepository;
	
	@Transactional(readOnly = true)
	@Override
	public List<Author> findAll() {
		return authorRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Author findById(Long id) {
		return authorRepository.findById(id).orElseThrow(NotFoundException::new);
	}

	@Transactional
	@Override
	public Author create(Author authorToCreate) {
		ofNullable(authorToCreate).orElseThrow(() -> new BusinessException("Author to create must not be null."));
		return authorRepository.save(authorToCreate);
	}

	@Override
	public Author update(Long id, Author authorToUpdate) {
		Author dbAuthor = findById(id);
		if (!dbAuthor.getId().equals(authorToUpdate.getId()))
			throw new BusinessException("Update IDs must be the same.");
		
		dbAuthor.setName(authorToUpdate.getName());
		dbAuthor.setBirthDate(authorToUpdate.getBirthDate());
		
		return authorRepository.save(dbAuthor);
	}

	@Override
	public void delete(Long id) {
		Author dbAuthor = findById(id);
		authorRepository.delete(dbAuthor);
	}

}
