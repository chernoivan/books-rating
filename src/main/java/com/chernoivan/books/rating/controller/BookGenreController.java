package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.bookgenre.BookGenreCreateDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePatchDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePutDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreReadDTO;
import com.chernoivan.books.rating.service.BookGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books/{bookId}/book-genres")
public class BookGenreController {

    @Autowired
    private BookGenreService bookGenreService;

    @GetMapping
    public BookGenreReadDTO getBookGenres(@PathVariable UUID id) {
        return bookGenreService.getBookGenres(id);
    }

    @PutMapping("/{id}")
    public BookGenreReadDTO putBookGenre(@PathVariable UUID bookId,
                                         @PathVariable UUID id,
                                         @RequestBody BookGenrePutDTO put) {
        return bookGenreService.updateBookGenre(bookId, id, put);
    }

    @PostMapping
    public BookGenreReadDTO createBookGenre(@PathVariable UUID bookId, BookGenreCreateDTO create) {
        return bookGenreService.createBookGenre(bookId, create);
    }

    @PatchMapping("/{id}")
    public BookGenreReadDTO patchBookGenre(@PathVariable UUID bookId,
                                           @PathVariable UUID id,
                                           @RequestBody BookGenrePatchDTO patch) {
        return bookGenreService.patchBookGenre(bookId, id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteBookGenre(@PathVariable UUID bookId, @PathVariable UUID id) {
        bookGenreService.deleteFilmGenre(bookId, id);
    }
}
