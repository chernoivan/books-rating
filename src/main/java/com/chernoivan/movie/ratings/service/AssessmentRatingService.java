package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.AssessmentRating;
import com.chernoivan.movie.ratings.dto.assessmentrating.*;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.AssessmentRatingRepository;
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
        dto.setUser(translationService.toRead(assessmentRating.getUser()));
        dto.setAssessment(translationService.toRead(assessmentRating.getAssessment()));
        dto.setLikeStatus(assessmentRating.getLikeStatus());
        return dto;
    }

    public AssessmentRatingReadDTO createAssessmentRating(AssessmentRatingCreateDTO create) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setLikeStatus(create.getLikeStatus());
        assessmentRating.setUser(applicationUserService.getUserRequired(create.getUser()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(create.getAssessment()));

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return translationService.toRead(assessmentRating);
    }

    public AssessmentRatingReadExtendedDTO createAssessmentRatingExtended(AssessmentRatingCreateDTO create) {
        AssessmentRating assessmentRating = new AssessmentRating();
        assessmentRating.setLikeStatus(create.getLikeStatus());
        assessmentRating.setUser(applicationUserService.getUserRequired(create.getUser()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(create.getAssessment()));

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return toReadExtended(assessmentRating);
    }

    public AssessmentRatingReadDTO patchAssessmentRating(UUID id, AssessmentRatingPatchDTO patch) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);

        if (patch.getUser() != null) {
            assessmentRating.setUser(applicationUserService.getUserRequired(patch.getUser()));
        }

        if (patch.getLikeStatus() != null) {
            assessmentRating.setLikeStatus(patch.getLikeStatus());
        }

        if (patch.getAssessment() != null) {
            assessmentRating.setAssessment(assessmentService.getAssessmentRequired(patch.getAssessment()));
        }

        assessmentRating = assessmentRatingRepository.save(assessmentRating);
        return translationService.toRead(assessmentRating);
    }

    public AssessmentRatingReadDTO updateAssessmentRating(UUID id, AssessmentRatingPutDTO put) {
        AssessmentRating assessmentRating = getAssessmentRatingRequired(id);
        assessmentRating.setUser(applicationUserService.getUserRequired(put.getUser()));
        assessmentRating.setAssessment(assessmentService.getAssessmentRequired(put.getAssessment()));
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
