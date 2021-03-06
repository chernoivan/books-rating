package com.chernoivan.books.rating.dto.assessmentrating;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingPutDTO {
    private Boolean likeStatus;
    private UUID userId;
    private UUID assessmentId;
}
