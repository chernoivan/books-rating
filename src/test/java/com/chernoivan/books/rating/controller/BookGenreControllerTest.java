package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.domain.BookGenre;
import com.chernoivan.books.rating.service.BookGenreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookGenreController.class)
@ActiveProfiles("test")
public class BookGenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookGenreService bookGenreService;


    @Test
    public void testDeleteAssessment() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/assessments/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(bookGenreService).deleteFilmGenre(id, id);
    }
}
