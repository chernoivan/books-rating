package com.chernoivan.books.rating.dto.assessmentrating;

import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingReadExtendedDTO {
    private UUID id;

    private ApplicationUserReadDTO user;

    private AssessmentReadDTO assessment;

    private Boolean likeStatus;
}
