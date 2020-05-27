package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.ApplicationUser;
import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.AssessmentRepository;
import com.chernoivan.books.rating.domain.Assessment;
import com.chernoivan.books.rating.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssessmentService {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private RepositoryHelper repositoryHelper;

    public AssessmentReadDTO getAssessment(UUID id) {
        Assessment assessment = getAssessmentRequired(id);
        return translationService.toRead(assessment);
    }

    public List<AssessmentReadDTO> getAssessmentsByUser(UUID userId) {
        List<Assessment> assessments = assessmentRepository.findByUserId(userId);
        if (assessments != null) {
            return assessments.stream().map(translationService::toRead).collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException(ApplicationUser.class, userId);
        }
    }


    public AssessmentReadDTO createAssessment(UUID userId, AssessmentCreateDTO create) {
        Assessment assessment = translationService.toEntity(create);

        assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, userId));

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public AssessmentReadDTO patchAssessment(UUID userId, UUID id, AssessmentPatchDTO patch) {
        Assessment assessment = getAssessmentRequired(id);

        translationService.patchEntity(patch, assessment);

        if (patch.getUserId() != null) {
            assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, userId));
        }

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public AssessmentReadDTO updateAssessment(UUID userId, UUID id, AssessmentPutDTO put) {
        Assessment assessment = getAssessmentRequired(id);

        translationService.updateEntity(put, assessment);
        assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, userId));

        assessment = assessmentRepository.save(assessment);
        return translationService.toRead(assessment);
    }

    public void deleteAssessment(UUID userId, UUID id) {
        List<Assessment> assessments = assessmentRepository.findByIdAndUserId(id, userId);
        if (assessments != null) {
            assessmentRepository.delete(getAssessmentRequired(id));
        } else {
            throw new EntityNotFoundException(ApplicationUser.class, userId, Assessment.class, id);
        }
    }

    public Assessment getAssessmentRequired(UUID id) {
        return assessmentRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Assessment.class, id));
    }

}
