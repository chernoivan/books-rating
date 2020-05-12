package com.chernoivan.movie.ratings.dto.memberassessment;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberAssessmentPatchDTO {
    private String role;
    private Double rating;
    private UUID film;
    private UUID memberType;
}