package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookPutDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.AssessmentRepository;
import com.chernoivan.books.rating.repository.BookRepository;
import com.chernoivan.books.rating.domain.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private AssessmentRepository assessmentRepository;

    public BookReadDTO getBook(UUID id) {
        Book book = getFilmRequired(id);
        return translationService.toRead(book);
    }

    public BookReadDTO createBook(BookCreateDTO create) {
        Book book = translationService.toEntity(create);

        book = bookRepository.save(book);
        return translationService.toRead(book);
    }

    public BookReadDTO patchBook(UUID id, BookPatchDTO patch) {
        Book book = getFilmRequired(id);

        translationService.patchEntity(patch, book);

        book = bookRepository.save(book);
        return translationService.toRead(book);
    }

    public BookReadDTO updateBook(UUID id, BookPutDTO put) {
        Book book = getFilmRequired(id);

        translationService.updateEntity(put, book);

        book = bookRepository.save(book);
        return translationService.toRead(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.delete(getFilmRequired(id));
    }

    @Transactional
    public void updateAverageMark(UUID bookId) {
        Double averageMark = assessmentRepository.calcAverageMarkOfBook(bookId);
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new EntityNotFoundException(Book.class, bookId));

        log.info("Setting new average mark of book: {}. Old value: {}, new value: {}", bookId,
                book.getBookRating(), averageMark);
        book.setBookRating(averageMark);
        bookRepository.save(book);

    }

    public Book getFilmRequired(UUID id) {
        return bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Book.class, id));
    }
}
