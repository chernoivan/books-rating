package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.author.AuthorCreateDTO;
import com.chernoivan.books.rating.dto.author.AuthorPatchDTO;
import com.chernoivan.books.rating.dto.author.AuthorPutDTO;
import com.chernoivan.books.rating.dto.author.AuthorReadDTO;
import com.chernoivan.books.rating.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping("/{id}")
    public AuthorReadDTO getAuthor(@PathVariable UUID id) {
        return authorService.getAuthor(id);
    }

    @PutMapping("/{id}")
    public AuthorReadDTO putAuthor(@PathVariable UUID id, @RequestBody AuthorPutDTO put) {
        return authorService.updateAuthor(id, put);
    }

    @PostMapping
    public AuthorReadDTO createAuthor(@RequestBody AuthorCreateDTO create) {
        return authorService.createAuthor(create);
    }

    @PatchMapping("/{id}")
    public AuthorReadDTO patchAuthor(@PathVariable UUID id, @RequestBody AuthorPatchDTO patch) {
        return authorService.patchAuthor(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable UUID id) {
        authorService.deleteAuthor(id);
    }
}
