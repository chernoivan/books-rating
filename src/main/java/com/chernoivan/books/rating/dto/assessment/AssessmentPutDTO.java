package com.chernoivan.books.rating.dto.assessment;

import com.chernoivan.books.rating.domain.enums.AssessmentType;
import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentPutDTO {
    private Integer likesCount;
    private String assessmentText;
    private Integer rating;
    private AssessmentType assessmentType;
    private UUID bookId;
    private UUID userId;
}
