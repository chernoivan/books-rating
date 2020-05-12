package com.chernoivan.movie.ratings.dto.memberassessment;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class MemberAssessmentReadDTO {
    private UUID id;
    private String role;
    private Double rating;
    private UUID film;
    private UUID memberType;
    private Instant createdAt;
    private Instant updatedAt;
}
