package com.chernoivan.movie.ratings.dto.assessmentrating;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingFilter {
    private UUID userId;
    private UUID assessmentId;
    private Boolean likeStatus;
}
