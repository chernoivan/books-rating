package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.security.UserDetailsServiceImpl;
import com.chernoivan.books.rating.service.ApplicationUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WithMockUser
@WebMvcTest(controllers = ApplicationUserController.class)
@ActiveProfiles("test")
public class NoSessionTest {
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private ApplicationUserService applicationUserService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testNoSession() throws Exception {
        UUID wrongId = UUID.randomUUID();

        Mockito.when(applicationUserService.getUser(wrongId)).thenReturn(new ApplicationUserReadDTO());

        MvcResult mvcResult = mvc.perform(get("/api/v1/users/{id}", wrongId))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertNull(mvcResult.getRequest().getSession(false));
    }
}
