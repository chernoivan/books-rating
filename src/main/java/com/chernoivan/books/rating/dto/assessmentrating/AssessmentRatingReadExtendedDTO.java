package com.chernoivan.books.rating.dto.assessmentrating;

import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AssessmentRatingReadExtendedDTO {
    private UUID id;

    private ApplicationUserReadDTO userId;

    private AssessmentReadDTO assessmentId;

    private Boolean likeStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
