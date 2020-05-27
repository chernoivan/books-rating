package com.chernoivan.books.rating.controller;

import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users/{userId}/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    @GetMapping("/{id}")
    public AssessmentReadDTO getAssessment(@PathVariable UUID id) {
        return assessmentService.getAssessment(id);
    }

    @GetMapping
    public List<AssessmentReadDTO> getAssessmentsByUser(@PathVariable UUID userId) {
        return assessmentService.getAssessmentsByUser(userId);
    }

    @PutMapping("/{id}")
    public AssessmentReadDTO putAssessment(@PathVariable UUID userId,
                                           @PathVariable UUID id,
                                           @RequestBody AssessmentPutDTO put) {
        return assessmentService.updateAssessment(userId, id, put);
    }

    @PostMapping
    public AssessmentReadDTO createAssessment(@PathVariable UUID userId, @RequestBody AssessmentCreateDTO create) {
        return assessmentService.createAssessment(userId, create);
    }

    @PatchMapping("/{id}")
    public AssessmentReadDTO patchAssessment(@PathVariable UUID userId,
                                             @PathVariable UUID id,
                                             @RequestBody AssessmentPatchDTO patch) {
        return assessmentService.patchAssessment(userId, id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteAssessment(@PathVariable UUID userId, @PathVariable UUID id) {
        assessmentService.deleteAssessment(userId, id);
    }
}
