package com.chernoivan.books.rating.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.chernoivan.books.rating.dto.assessmentrating.*;
import com.chernoivan.books.rating.service.AssessmentRatingService;
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

import java.util.List;
import java.util.UUID;

@WithMockUser
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = AssessmentRatingController.class)
@ActiveProfiles("test")
public class AssessmentRatingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AssessmentRatingService assessmentRatingService;

    @Test
    public void testGetAssessmentRating() throws Exception {
        AssessmentRatingReadDTO assessmentRatingRead = createAssessmentRatingRead();

        Mockito.when(assessmentRatingService.getAssessmentRating(assessmentRatingRead.getId()))
                .thenReturn(assessmentRatingRead);

        String resultJson = mvc.perform(get("/api/v1/assessment-ratings/{id}", assessmentRatingRead.getId()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        System.out.println(resultJson);
        AssessmentRatingReadDTO actualAssessmentRating = objectMapper.readValue(resultJson,
                AssessmentRatingReadDTO.class);
        Assertions.assertThat(actualAssessmentRating).isEqualToIgnoringGivenFields(assessmentRatingRead,
                "userId");

        Mockito.verify(assessmentRatingService).getAssessmentRating(assessmentRatingRead.getId());
    }

    @Test
    public void testCreateAssessmentRating() throws Exception {
        AssessmentRatingCreateDTO create = new AssessmentRatingCreateDTO();
        create.setLikeStatus(true);

        AssessmentRatingReadDTO read = createAssessmentRatingRead();

        Mockito.when(assessmentRatingService.createAssessmentRating(create)).thenReturn(read);

        String resultJson = mvc.perform(post("/api/v1/assessment-ratings")
                .content(objectMapper.writeValueAsString(create))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AssessmentRatingReadDTO actual = objectMapper.readValue(resultJson, AssessmentRatingReadDTO.class);
        Assertions.assertThat(actual).isEqualToComparingFieldByField(read);
    }

    @Test
    public void testPatchAssessmentRating() throws Exception {
        AssessmentRatingPatchDTO patch = new AssessmentRatingPatchDTO();
        patch.setLikeStatus(true);

        AssessmentRatingReadDTO read = createAssessmentRatingRead();

        Mockito.when(assessmentRatingService.patchAssessmentRating(read.getId(), patch)).thenReturn(read);

        String resultJson = mvc.perform(patch("/api/v1/assessment-ratings/{id}", read.getId().toString())
                .content(objectMapper.writeValueAsString(patch))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        AssessmentRatingReadDTO actual = objectMapper.readValue(resultJson, AssessmentRatingReadDTO.class);
        Assert.assertEquals(read, actual);
    }

    @Test
    public void testDeleteAssessmentRating() throws Exception {
        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/assessment-ratings/{id}", id.toString())).andExpect(status().isOk());

        Mockito.verify(assessmentRatingService).deleteAssessmentRating(id);
    }

    @Test
    public void testGetAssessmentRatings() throws Exception {
        AssessmentRatingFilter filter = new AssessmentRatingFilter();
        filter.setAssessmentId(UUID.randomUUID());
        filter.setUserId(UUID.randomUUID());
        filter.setLikeStatus(true);

        AssessmentRatingReadDTO read = new AssessmentRatingReadDTO();
        read.setAssessmentId(filter.getAssessmentId());
        read.setUserId(filter.getUserId());
        read.setLikeStatus(true);
        read.setId(UUID.randomUUID());
        List<AssessmentRatingReadDTO> expectedResult = List.of(read);
        Mockito.when(assessmentRatingService.getAssessmentRatings(filter)).thenReturn(expectedResult);

        String resultJson = mvc.perform(get("/api/v1/assessment-ratings")
                .param("userId", filter.getUserId().toString())
                .param("assessmentId", filter.getAssessmentId().toString())
                .param("likeStatus", filter.getLikeStatus().toString()))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        List<AssessmentRatingReadDTO> actual = objectMapper.readValue(resultJson, new TypeReference<>() {
        });
        Assert.assertEquals(expectedResult, actual);
    }

    private AssessmentRatingReadDTO createAssessmentRatingRead() {
        AssessmentRatingReadDTO read = new AssessmentRatingReadDTO();
        read.setId(UUID.randomUUID());
        read.setLikeStatus(true);
        return read;
    }

    private AssessmentRatingReadExtendedDTO createAssessmentRatingReadExtended() {
        AssessmentRatingReadExtendedDTO read = new AssessmentRatingReadExtendedDTO();
        read.setId(UUID.randomUUID());
        read.setLikeStatus(true);
        return read;
    }
}
