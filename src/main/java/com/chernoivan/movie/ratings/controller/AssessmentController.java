package com.chernoivan.movie.ratings.controller;

import com.chernoivan.movie.ratings.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentReadDTO;
import com.chernoivan.movie.ratings.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/assessments")
public class AssessmentController {
    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/{id}")
    public AssessmentReadDTO getAssessment(@PathVariable UUID id) {
        return assessmentService.getAssessment(id);
    }

    @PutMapping("/{id}")
    public AssessmentReadDTO putAssessment(@PathVariable UUID id, @RequestBody AssessmentPutDTO put) {
        return assessmentService.updateAssessment(id, put);
    }

    @PostMapping
    public AssessmentReadDTO createAssessment(@RequestBody AssessmentCreateDTO create) {
        return assessmentService.createAssessment(create);
    }

    @PatchMapping("/{id}")
    public AssessmentReadDTO patchAssessment(@PathVariable UUID id, @RequestBody AssessmentPatchDTO patch) {
        return assessmentService.patchAssessment(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteAssessment(@PathVariable UUID id) {
        assessmentService.deleteAssessment(id);
    }
}
