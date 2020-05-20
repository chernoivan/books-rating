package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreCreateDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePatchDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePutDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.BookGenreRepository;
import com.chernoivan.books.rating.domain.BookGenre;
import com.chernoivan.books.rating.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookGenreService {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private RepositoryHelper repositoryHelper;

    public BookGenreReadDTO getBookGenres(UUID id) {
        BookGenre bookGenre = getBookGenreRequired(id);
        return translationService.toRead(bookGenre);
    }

    public BookGenreReadDTO createBookGenre(UUID bookId, BookGenreCreateDTO create) {
        BookGenre bookGenre = translationService.toEntity(create);
        bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, bookId));
        bookGenre = bookGenreRepository.save(bookGenre);

        return  translationService.toRead(bookGenre);
    }

    public BookGenreReadDTO patchBookGenre(UUID bookId, UUID id, BookGenrePatchDTO patch) {
        BookGenre bookGenre = getBookGenreRequired(id);

        translationService.patchEntity(patch, bookGenre);
        bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, bookId));

        bookGenre = bookGenreRepository.save(bookGenre);
        return translationService.toRead(bookGenre);
    }

    public BookGenreReadDTO updateBookGenre(UUID bookId, UUID id, BookGenrePutDTO put) {
        BookGenre bookGenre = getBookGenreRequired(id);

        translationService.updateEntity(put, bookGenre);
        bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, bookId));

        bookGenre = bookGenreRepository.save(bookGenre);
        return translationService.toRead(bookGenre);
    }

    public void deleteFilmGenre(UUID bookId, UUID id) {
        bookGenreRepository.delete(getBookGenreRequired(id));
    }

    public BookGenre getBookGenreRequired(UUID id) {
        return bookGenreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(BookGenre.class, id));
    }
}
