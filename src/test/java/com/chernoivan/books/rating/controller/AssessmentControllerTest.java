package com.chernoivan.books.rating.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chernoivan.books.rating.domain.enums.AssessmentType;
import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.security.UserDetailsServiceImpl;
import com.chernoivan.books.rating.service.AssessmentService;
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

import java.util.UUID;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AssessmentController.class)
@ActiveProfiles("test")
public class AssessmentControllerTest {
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssessmentService assessmentService;

    @Test
    public void testGetAssessment() throws Exception {
        AssessmentReadDTO read = createAssessmentRead();

        Mockito.when(assessmentService.getAssessment(read.getId())).thenReturn(read);

        String resultJson = mvc.perform(get("/api/v1/users/"+read.getUserId()+"/assessments/{id}", read.getId()))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        System.out.println(resultJson);
        AssessmentReadDTO actualAssessment = objectMapper.readValue(resultJson, AssessmentReadDTO.class);
        Assertions.assertThat(actualAssessment).isEqualToComparingFieldByField(read);

        Mockito.verify(assessmentService).getAssessment(read.getId());
    }

    @Test
    public void testCreateAssessment() throws Exception {
        AssessmentCreateDTO create = new AssessmentCreateDTO();
        create.setAssessmentText("great movie");
        create.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        create.setLikesCount(21);
        create.setRating(8);
        create.setUserId(UUID.randomUUID());

        AssessmentReadDTO read = createAssessmentRead();

        Mockito.when(assessmentService.createAssessment(create.getUserId(), create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/users/"+create.getUserId()+"/assessments")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AssessmentReadDTO actualAssessment = objectMapper.readValue(resultJson, AssessmentReadDTO.class);
        Assertions.assertThat(actualAssessment).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchAssessment() throws Exception {
        AssessmentPatchDTO patch = new AssessmentPatchDTO();
        patch.setRating(10);
        patch.setUserId(UUID.randomUUID());

        AssessmentReadDTO read = createAssessmentRead();

        Mockito.when(assessmentService.patchAssessment(patch.getUserId(), read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/users/"+patch.getUserId().toString()+"/assessments/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AssessmentReadDTO actualAssessment = objectMapper.readValue(resultJson, AssessmentReadDTO.class);
        Assert.assertEquals(read, actualAssessment);
    }

    @Test
    public void testPutAssessment() throws Exception {
        AssessmentPutDTO put = new AssessmentPutDTO();
        put.setRating(10);
        put.setUserId(UUID.randomUUID());

        AssessmentReadDTO read = createAssessmentRead();

        Mockito.when(assessmentService.updateAssessment(put.getUserId(), read.getId(), put)).thenReturn(read);

        String resultJson = mvc.perform(put("/api/v1/users/"+put.getUserId().toString()+"/assessments/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(put))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AssessmentReadDTO actualAssessment = objectMapper.readValue(resultJson, AssessmentReadDTO.class);
        Assert.assertEquals(read, actualAssessment);
    }

    @Test
    public void testDeleteAssessment() throws Exception {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        mvc.perform(delete("/api/v1/users/"+userId.toString()+"/assessments/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(assessmentService).deleteAssessment(userId, id);
    }

    private AssessmentReadDTO createAssessmentRead() {
        AssessmentReadDTO read = new AssessmentReadDTO();
        read.setId(UUID.randomUUID());
        read.setUserId(UUID.randomUUID());
        read.setAssessmentText("great movie");
        read.setAssessmentType(AssessmentType.FILM_ASSESSMENT);
        read.setLikesCount(23);
        read.setRating(8);
        return read;
    }
}
