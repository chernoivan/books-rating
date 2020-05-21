package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.Author;
import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.dto.author.AuthorCreateDTO;
import com.chernoivan.books.rating.dto.author.AuthorPatchDTO;
import com.chernoivan.books.rating.dto.author.AuthorPutDTO;
import com.chernoivan.books.rating.dto.author.AuthorReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TranslationService translationService;

    public AuthorReadDTO getAuthor(UUID id) {
        Author author = getAuthorRequired(id);
        return translationService.toRead(author);
    }

    public AuthorReadDTO createAuthor(AuthorCreateDTO create) {
        Author author = translationService.toEntity(create);

        author = authorRepository.save(author);
        return translationService.toRead(author);
    }

    public AuthorReadDTO patchAuthor(UUID id, AuthorPatchDTO patch) {
        Author author = getAuthorRequired(id);

        translationService.patchEntity(patch, author);

        author = authorRepository.save(author);
        return translationService.toRead(author);
    }


    public AuthorReadDTO updateAuthor(UUID id, AuthorPutDTO put) {
        Author author = getAuthorRequired(id);

        translationService.updateEntity(put, author);

        author = authorRepository.save(author);
        return translationService.toRead(author);
    }

    public void deleteAuthor(UUID id) {
        authorRepository.delete(getAuthorRequired(id));
    }

    public Author getAuthorRequired(UUID id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Book.class, id));
    }
}
