package com.chernoivan.movie.ratings.dto.assessmentrating;

import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentRatingPutDTO {
    private Boolean likeStatus;
    private UUID user;
    private UUID assessment;
}
