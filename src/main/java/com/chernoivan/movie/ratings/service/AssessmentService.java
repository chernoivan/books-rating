package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.Assessment;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.AssessmentRepository;
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
