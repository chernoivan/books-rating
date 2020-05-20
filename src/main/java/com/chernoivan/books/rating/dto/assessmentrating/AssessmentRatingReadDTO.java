package com.chernoivan.books.rating.dto.assessmentrating;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AssessmentRatingReadDTO {
    private UUID id;
    private Boolean likeStatus;
    private UUID user;
    private UUID assessment;
    private Instant createdAt;
    private Instant updatedAt;
}
