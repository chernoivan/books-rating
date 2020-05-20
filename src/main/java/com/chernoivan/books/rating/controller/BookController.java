package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookPutDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
import com.chernoivan.books.rating.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public BookReadDTO getBook(@PathVariable UUID id) {
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public BookReadDTO putBook(@PathVariable UUID id, @RequestBody BookPutDTO put) {
        return bookService.updateBook(id, put);
    }

    @PostMapping
    public BookReadDTO createBook(@RequestBody BookCreateDTO create) {
        return bookService.createBook(create);
    }

    @PatchMapping("/{id}")
    public BookReadDTO patchBook(@PathVariable UUID id, @RequestBody BookPatchDTO patch) {
        return bookService.patchBook(id, patch);
    }

//    @PostMapping("/authors/{authorId}/books/{id}")
//    public List<BookReadDTO> addAuthorToBook(@PathVariable UUID authorId, @PathVariable UUID id) {
//        return bookService.addAuthorToBook(authorId, id);
//    }


    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
    }
}
