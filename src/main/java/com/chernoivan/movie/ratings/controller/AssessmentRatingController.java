package com.chernoivan.movie.ratings.controller;

import com.chernoivan.movie.ratings.dto.assessmentrating.*;
import com.chernoivan.movie.ratings.service.AssessmentRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/assessment-ratings")
public class AssessmentRatingController {

    @Autowired
    private AssessmentRatingService assessmentRatingService;

    @GetMapping("/{id}")
    public AssessmentRatingReadDTO getAssessmentRating(@PathVariable UUID id) {
        return assessmentRatingService.getAssessmentRating(id);
    }

    @GetMapping
    public List<AssessmentRatingReadDTO> getAssessmentRatings(AssessmentRatingFilter filter) {
        return assessmentRatingService.getAssessmentRatings(filter);
    }

    @PostMapping
    public AssessmentRatingReadDTO createAssessmentRating(@RequestBody AssessmentRatingCreateDTO createDTO) {
        return assessmentRatingService.createAssessmentRating(createDTO);
    }

    @PatchMapping("/{id}")
    public AssessmentRatingReadDTO patchAssessmentRating(@PathVariable UUID id,
                                                         @RequestBody AssessmentRatingPatchDTO patch) {
        return assessmentRatingService.patchAssessmentRating(id, patch);
    }

    @PutMapping("/{id}")
    public AssessmentRatingReadDTO putAssessmentRating(@PathVariable UUID id, @RequestBody AssessmentRatingPutDTO put) {
        return assessmentRatingService.updateAssessmentRating(id, put);
    }

    @DeleteMapping("/{id}")
    public void deleteAssessmentRating(@PathVariable UUID id) {
        assessmentRatingService.deleteAssessmentRating(id);
    }
}
