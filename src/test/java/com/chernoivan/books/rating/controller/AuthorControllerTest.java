package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.author.AuthorCreateDTO;
import com.chernoivan.books.rating.dto.author.AuthorPatchDTO;
import com.chernoivan.books.rating.dto.author.AuthorPutDTO;
import com.chernoivan.books.rating.dto.author.AuthorReadDTO;
import com.chernoivan.books.rating.service.AuthorService;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AuthorController.class)
@ActiveProfiles("test")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorService authorService;

    @Test
    public void testGetAuthor() throws Exception {
        AuthorReadDTO read = createAuthorRead();

        Mockito.when(authorService.getAuthor(read.getId())).thenReturn(read);

        String resultJson = mvc.perform(get("/api/v1/authors/{id}", read.getId()))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        System.out.println(resultJson);
        AuthorReadDTO actualAuthor = objectMapper.readValue(resultJson, AuthorReadDTO.class);
        Assertions.assertThat(actualAuthor).isEqualToComparingFieldByField(read);

        Mockito.verify(authorService).getAuthor(read.getId());
    }

    @Test
    public void testCreateAuthor() throws Exception {
        AuthorCreateDTO create = new AuthorCreateDTO();
        create.setFirstName("Hello");
        create.setLastName("world");

        AuthorReadDTO read = createAuthorRead();

        Mockito.when(authorService.createAuthor(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/authors")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        
        AuthorReadDTO actualAuthor = objectMapper.readValue(resultJson, AuthorReadDTO.class);
        Assertions.assertThat(actualAuthor).isEqualToComparingFieldByField(read);
    }
    
    @Test
    public void testPatchAuthor() throws Exception {
        AuthorPatchDTO patch = new AuthorPatchDTO();
        patch.setFirstName("Hello");
        patch.setLastName("World");

        AuthorReadDTO read = createAuthorRead();

        Mockito.when(authorService.patchAuthor(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/authors/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthorReadDTO actualAuthor = objectMapper.readValue(resultJson, AuthorReadDTO.class);
        Assert.assertEquals(read, actualAuthor);
    }

    @Test
    public void testPutAuthor() throws Exception {
        AuthorPutDTO put = new AuthorPutDTO();
        put.setFirstName("Hello");
        put.setLastName("World");

        AuthorReadDTO read = createAuthorRead();

        Mockito.when(authorService.updateAuthor(read.getId(), put)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/authors/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(put))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AuthorReadDTO actualAuthor = objectMapper.readValue(resultJson, AuthorReadDTO.class);
        Assert.assertEquals(read, actualAuthor);
    }


    @Test
    public void testDeleteAuthor() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/authors/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(authorService).deleteAuthor(id);
    }

    private AuthorReadDTO createAuthorRead() {
        AuthorReadDTO read = new AuthorReadDTO();
        read.setId(UUID.randomUUID());
        read.setFirstName("Hello");
        read.setLastName("World");
        return read;
    }
}
