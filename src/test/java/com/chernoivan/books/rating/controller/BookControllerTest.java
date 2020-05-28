package com.chernoivan.books.rating.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.chernoivan.books.rating.domain.Book;
import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookPutDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.exception.hander.ErrorInfo;
import com.chernoivan.books.rating.exception.hander.RestExceptionHandler;
import com.chernoivan.books.rating.security.UserDetailsServiceImpl;
import com.chernoivan.books.rating.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("test")
public class BookControllerTest {
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetBook() throws Exception {
        BookReadDTO read = createBookRead();

        Mockito.when(bookService.getBook(read.getId())).thenReturn(read);

        String resultJson = mvc.perform(get("/api/v1/books/{id}", read.getId()))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        System.out.println(resultJson);
        BookReadDTO actualBook = objectMapper.readValue(resultJson, BookReadDTO.class);
        Assertions.assertThat(actualBook).isEqualToComparingFieldByField(read);

        Mockito.verify(bookService).getBook(read.getId());
    }

    @Test
    public void testGetBookWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(Book.class, wrongId);
        Mockito.when(bookService.getBook(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/books/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }

    @Test
    public void testGetWrongUUID() throws Exception {
        String resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/v1/books/1234"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        RestExceptionHandler actualError = objectMapper.readValue(resultJson, RestExceptionHandler.class);
        Assertions.assertThat(actualError).isEqualToComparingFieldByField(ErrorInfo.class);
    }

    @Test
    public void testCreateBook() throws Exception {
        BookCreateDTO create = new BookCreateDTO();
        create.setTitle("hello");
        create.setBookRating(10.0);

        BookReadDTO read = createBookRead();

        Mockito.when(bookService.createBook(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/books")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookReadDTO actualBook = objectMapper.readValue(resultJson, BookReadDTO.class);
        Assertions.assertThat(actualBook).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchBook() throws Exception {
        BookPatchDTO patch = new BookPatchDTO();
        patch.setTitle("hello");
        patch.setBookRating(10.0);

        BookReadDTO read = createBookRead();

        Mockito.when(bookService.patchBook(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/books/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookReadDTO actualBook = objectMapper.readValue(resultJson, BookReadDTO.class);
        Assert.assertEquals(read, actualBook);
    }

    @Test
    public void testPutBook() throws Exception {
        BookPutDTO put = new BookPutDTO();
        put.setTitle("hello");
        put.setBookRating(10.0);

        BookReadDTO read = createBookRead();

        Mockito.when(bookService.updateBook(read.getId(), put)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/books/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(put))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookReadDTO actualBook = objectMapper.readValue(resultJson, BookReadDTO.class);
        Assert.assertEquals(read, actualBook);

    }

    @Test
    public void testDeleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/books/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(bookService).deleteBook(id);
    }

    private BookReadDTO createBookRead() {
        BookReadDTO read = new BookReadDTO();
        read.setId(UUID.randomUUID());
        read.setTitle("Hello");
        return read;
    }
}
