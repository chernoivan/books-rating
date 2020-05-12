package com.chernoivan.movie.ratings.dto.assessment;

import com.chernoivan.movie.ratings.domain.enums.AssessmentType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AssessmentReadDTO {
    private UUID id;
    private Integer likesCount;
    private String assessmentText;
    private Integer rating;
    private AssessmentType assessmentType;
    private UUID film;
    private UUID memberAssessment;
    private UUID user;
    private Instant createdAt;
    private Instant updatedAt;
}
