package com.chernoivan.books.rating.dto.assessment;

import com.chernoivan.books.rating.domain.enums.AssessmentType;
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
    private UUID bookId;
    private UUID userId;
    private Instant createdAt;
    private Instant updatedAt;
}
