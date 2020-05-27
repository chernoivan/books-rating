package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.dto.assessmentrating.*;
import com.chernoivan.books.rating.exception.EntityNotFoundException;
import com.chernoivan.books.rating.repository.AssessmentRatingRepository;
import com.chernoivan.books.rating.domain.AssessmentRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssessmentRatingService {

    @Autowired
    private AssessmentRatingRepository assessmentRatingRepository;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private AssessmentService assessmentService;

    public AssessmentRatingReadDTO getAssessmentRating(UUID id) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);
        return translationService.toRead(assessmentRating);
    }

    public List<AssessmentRatingReadDTO> getAssessmentRatings(AssessmentRatingFilter filter) {
        List<AssessmentRating> assessmentRatings = assessmentRatingRepository.findByFilter(filter);
        return assessmentRatings.stream().map(translationService::toRead).collect(Collectors.toList());
    }

    public AssessmentRatingReadExtendedDTO getAssessmentRatingExtended(UUID id) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);
        return toReadExtended(assessmentRating);
    }

    private AssessmentRatingReadExtendedDTO toReadExtended(AssessmentRating assessmentRating) {
        AssessmentRatingReadExtendedDTO dto = new AssessmentRatingReadExtendedDTO();
        dto.setId(assessmentRating.getId());
        dto.setUserId(translationService.toRead(assessmentRating.getUser()));
        dto.setAssessmentId(translationService.toRead(assessmentRating.getAssessment()));
        dto.setLikeStatus(assessmentRating.getLikeStatus());
        dto.setCreatedAt(assessmentRating.getCreatedAt());
        dto.setUpdatedAt(assessmentRating.getUpdatedAt());
        return dto;
    }

    public AssessmentRatingReadDTO createAssessmentRating(AssessmentRatingCreateDTO create) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setLikeStatus(create.getLikeStatus());
        assessmentRating.setUser(applicationUserService.getUserRequired(create.getUserId()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(create.getAssessmentId()));

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return translationService.toRead(assessmentRating);
    }

    public AssessmentRatingReadExtendedDTO createAssessmentRatingExtended(AssessmentRatingCreateDTO create) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setLikeStatus(create.getLikeStatus());
        assessmentRating.setUser(applicationUserService.getUserRequired(create.getUserId()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(create.getAssessmentId()));

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return toReadExtended(assessmentRating);
    }

    public AssessmentRatingReadDTO patchAssessmentRating(UUID id, AssessmentRatingPatchDTO patch) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);

        if (patch.getUserId() != null) {
            assessmentRating.setUser(applicationUserService.getUserRequired(patch.getUserId()));
        }

        if (patch.getLikeStatus() != null) {
            assessmentRating.setLikeStatus(patch.getLikeStatus());
        }

        if (patch.getAssessmentId() != null) {
            assessmentRating.setAssessment(assessmentService.getAssessmentRequired(patch.getAssessmentId()));
        }

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return translationService.toRead(assessmentRating);
    }

    public AssessmentRatingReadDTO updateAssessmentRating(UUID id, AssessmentRatingPutDTO put) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);
        assessmentRating.setUser(applicationUserService.getUserRequired(put.getUserId()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(put.getAssessmentId()));
        assessmentRating.setLikeStatus(put.getLikeStatus());

        return translationService.toRead(assessmentRating);
    }

    public void deleteAssessmentRating(UUID id) {
        assessmentRatingRepository.delete(getAssessmentRatingRequired(id));
    }

    private AssessmentRating getAssessmentRatingRequired(UUID id) {
        return assessmentRatingRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(AssessmentRating.class, id));
    }

}
