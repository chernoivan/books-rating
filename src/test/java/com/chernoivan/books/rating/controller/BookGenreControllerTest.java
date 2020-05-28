package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.domain.enums.BookGenres;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreCreateDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePatchDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePutDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreReadDTO;
import com.chernoivan.books.rating.security.UserDetailsServiceImpl;
import com.chernoivan.books.rating.service.BookGenreService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BookGenreController.class)
@ActiveProfiles("test")
public class BookGenreControllerTest {
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookGenreService bookGenreService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetBookGenres() throws Exception {
        BookGenreReadDTO read = createBookGenreRead();
        BookGenreReadDTO read1 = new BookGenreReadDTO();
        read1.setId(UUID.randomUUID());
        read1.setBookId(read.getBookId());
        read1.setBookGenres(BookGenres.ART);
        List<BookGenreReadDTO> readDTOS = new ArrayList<>();
        readDTOS.add(read);
        readDTOS.add(read1);
        Mockito.when(bookGenreService.getBookGenres(read.getBookId())).thenReturn(readDTOS);

        String resultJson = mvc.perform(get("/api/v1/books/{bookId}/book-genres", read.getBookId()))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        System.out.println(resultJson);
        List<BookGenreReadDTO> actualBookGenres = objectMapper.readValue(resultJson, new TypeReference<List<BookGenreReadDTO>>(){});
        Assertions.assertThat(actualBookGenres).isEqualTo(readDTOS);
        Mockito.verify(bookGenreService).getBookGenres(read.getBookId());
    }

    @Test
    public void testCreateBookGenre() throws Exception {
        BookGenreCreateDTO create = new BookGenreCreateDTO();
        create.setBookGenres(BookGenres.ADVENTURE);
        create.setBookId(UUID.randomUUID());

        BookGenreReadDTO read = createBookGenreRead();

        Mockito.when(bookGenreService.createBookGenre(create.getBookId(), create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/books/"+create.getBookId()+"/book-genres")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookGenreReadDTO actualBookGenre = objectMapper.readValue(resultJson, BookGenreReadDTO.class);
        Assertions.assertThat(actualBookGenre).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchBookGenre() throws Exception{
        BookGenrePatchDTO patch = new BookGenrePatchDTO();
        patch.setBookId(UUID.randomUUID());
        patch.setBookGenres(BookGenres.ADVENTURE);

        BookGenreReadDTO read = createBookGenreRead();

        Mockito.when(bookGenreService.patchBookGenre(patch.getBookId(), read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/books/{bookId}/book-genres/{id}", patch.getBookId().toString(), read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookGenreReadDTO actualBookGenre = objectMapper.readValue(resultJson, BookGenreReadDTO.class);
        Assert.assertEquals(read, actualBookGenre);
    }

    @Test
    public  void  testPutBookGenre() throws Exception {
        BookGenrePutDTO put = new BookGenrePutDTO();
        put.setBookId(UUID.randomUUID());
        put.setBookGenres(BookGenres.ADVENTURE);

        BookGenreReadDTO read = createBookGenreRead();

        Mockito.when(bookGenreService.updateBookGenre(put.getBookId(), read.getId(), put)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/books/{bookId}/book-genres/{id}", put.getBookId().toString(), read.getId().toString())
                .content(objectMapper.writeValueAsString(put))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        BookGenreReadDTO actualBookGenre = objectMapper.readValue(resultJson, BookGenreReadDTO.class);
        Assert.assertEquals(read, actualBookGenre);
    }

    @Test
    public void testDeleteBookGenre() throws Exception {
        UUID id = UUID.randomUUID();
        UUID book_id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/books/{book_id}/book-genres/{id}", book_id.toString(), id.toString())).andExpect(status().isOk());

        Mockito.verify(bookGenreService).deleteFilmGenre(book_id, id);
    }

    private BookGenreReadDTO createBookGenreRead() {
        BookGenreReadDTO read = new BookGenreReadDTO();
        read.setId(UUID.randomUUID());
        read.setBookGenres(BookGenres.ADVENTURE);
        read.setBookId(UUID.randomUUID());
        return read;
    }
}
