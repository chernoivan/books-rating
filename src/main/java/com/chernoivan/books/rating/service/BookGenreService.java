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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookGenreService {

    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private RepositoryHelper repositoryHelper;

    public List<BookGenreReadDTO> getBookGenres(UUID bookId) {
        List<BookGenre> bookGenres = bookGenreRepository.findByBookId(bookId);
        if (bookGenres != null) {
            return bookGenres.stream().map(translationService::toRead).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException(Book.class, bookId);
        }
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

        if (patch.getBookId() != null) {
            bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, bookId));
        }

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
        List<BookGenre> bookGenres = bookGenreRepository.findByIdAndBookId(id, bookId);
        if (bookGenres != null) {
            bookGenreRepository.delete(getBookGenreRequired(id));
        } else {
            throw new EntityNotFoundException(Book.class, bookId, BookGenre.class, id);
        }
    }

    public BookGenre getBookGenreRequired(UUID id) {
        return bookGenreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(BookGenre.class, id));
    }
}
