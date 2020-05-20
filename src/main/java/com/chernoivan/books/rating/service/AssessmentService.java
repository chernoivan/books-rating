package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.AssessmentRepository;
import com.chernoivan.books.rating.domain.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private TranslationService translationService;

    public AssessmentReadDTO getAssessment(UUID id) {
        Assessment assessment = getAssessmentRequired(id);
        return translationService.toRead(assessment);
    }

    public AssessmentReadDTO createAssessment(AssessmentCreateDTO create) {
        Assessment assessment = translationService.toEntity(create);

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public AssessmentReadDTO patchAssessment(UUID id, AssessmentPatchDTO patch) {
        Assessment assessment = getAssessmentRequired(id);

        translationService.patchEntity(patch, assessment);

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public AssessmentReadDTO updateAssessment(UUID id, AssessmentPutDTO put) {
        Assessment assessment = getAssessmentRequired(id);

        translationService.updateEntity(put, assessment);

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public void deleteAssessment(UUID id) {
        assessmentRepository.delete(getAssessmentRequired(id));
    }

    public Assessment getAssessmentRequired(UUID id) {
        return assessmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Assessment.class, id));
    }

}