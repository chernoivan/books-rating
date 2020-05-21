package com.chernoivan.books.rating.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.chernoivan.books.rating.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookController.class)
@ActiveProfiles("test")
public class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testDeleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/books/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(bookService).deleteBook(id);
    }
}
