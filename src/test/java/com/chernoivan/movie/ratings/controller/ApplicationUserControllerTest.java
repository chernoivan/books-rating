package com.chernoivan.movie.ratings.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chernoivan.movie.ratings.domain.ApplicationUser;
import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.exception.hander.ErrorInfo;
import com.chernoivan.movie.ratings.exception.hander.RestExceptionHandler;
import com.chernoivan.movie.ratings.service.ApplicationUserService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ApplicationUserController.class)
@ActiveProfiles("test")
public class ApplicationUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ApplicationUserService applicationUserService;

    @Test
    public void testGetUser() throws Exception {
        ApplicationUserReadDTO user = createUserRead();

        Mockito.when(applicationUserService.getUser(user.getId())).thenReturn(user);

        String resultJson = mvc.perform(get("/api/v1/users/{id}", user.getId()))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        System.out.println(resultJson);
        ApplicationUserReadDTO actualUser = objectMapper.readValue(resultJson, ApplicationUserReadDTO.class);
        Assertions.assertThat(actualUser).isEqualToIgnoringGivenFields(user, "access");

        Mockito.verify(applicationUserService).getUser(user.getId());
    }

    @Test
    public void testGetUserWrongId() throws Exception {
        UUID wrongId = UUID.randomUUID();

        EntityNotFoundException exception = new EntityNotFoundException(ApplicationUser.class, wrongId);
        Mockito.when(applicationUserService.getUser(wrongId)).thenThrow(exception);

        String resultJson = mvc.perform(get("/api/v1/users/{id}", wrongId))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(resultJson.contains(exception.getMessage()));
    }


    @Test
    public void testGetWrongUUID() throws Exception {
        String resultJson = mvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1234"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        RestExceptionHandler actualError = objectMapper.readValue(resultJson, RestExceptionHandler.class);
        Assertions.assertThat(actualError).isEqualToComparingFieldByField(ErrorInfo.class);
    }

    @Test
    public void testCreateUser() throws Exception {
        ApplicationUserCreateDTO create = new ApplicationUserCreateDTO();
        create.setUsername("Alex");
        create.setEmail("alexchernoivan@gmail.com");

        ApplicationUserReadDTO read = createUserRead();

        Mockito.when(applicationUserService.createUser(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/users")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ApplicationUserReadDTO actualUser = objectMapper.readValue(resultJson, ApplicationUserReadDTO.class);
        Assertions.assertThat(actualUser).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchUser() throws Exception {
        ApplicationUserPatchDTO patchDTO = new ApplicationUserPatchDTO();
        patchDTO.setUsername("Alex");
        patchDTO.setEmail("alexchernoivan@gmail.com");

        ApplicationUserReadDTO read = createUserRead();

        Mockito.when(applicationUserService.patchUser(read.getId(), patchDTO)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/users/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ApplicationUserReadDTO actualUser = objectMapper.readValue(resultJson, ApplicationUserReadDTO.class);
        Assert.assertEquals(read, actualUser);
    }

    @Test
    public void testPutUser() throws Exception {
        ApplicationUserPutDTO putDTO = new ApplicationUserPutDTO();
        putDTO.setUsername("Alex");
        putDTO.setEmail("alexchernoivan@gmail.com");
        putDTO.setAccess(AccessLevelType.BLOCKED);

        ApplicationUserReadDTO read = createUserRead();

        Mockito.when(applicationUserService.updateUser(read.getId(), putDTO)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/users/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(putDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ApplicationUserReadDTO actualUser = objectMapper.readValue(resultJson, ApplicationUserReadDTO.class);
        Assert.assertEquals(read, actualUser);
    }

    @Test
    public void testDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/users/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(applicationUserService).deleteUser(id);
    }

    private ApplicationUserReadDTO createUserRead() {
        ApplicationUserReadDTO read = new ApplicationUserReadDTO();
        read.setId(UUID.randomUUID());
        read.setUsername("Alex");
        read.setEmail("alexchernoivan@gmail.com");
        return read;
    }
}
