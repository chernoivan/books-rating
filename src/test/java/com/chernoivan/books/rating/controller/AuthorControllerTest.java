package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.service.AuthorService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthorController.class)
@ActiveProfiles("test")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testDeleteAuthor() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/authors/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(authorService).deleteAuthor(id);
    }
}
