package com.chernoivan.books.rating.dto.assessmentrating;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingCreateDTO {
    private Boolean likeStatus;
    private UUID userId;
    private UUID assessmentId;
}
