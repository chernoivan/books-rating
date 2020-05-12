package com.chernoivan.movie.ratings.dto.assessmentrating;

import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentReadDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingReadExtendedDTO {
    private UUID id;

    private ApplicationUserReadDTO user;

    private AssessmentReadDTO assessment;

    private Boolean likeStatus;
}
