package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.dto.assessmentrating.AssessmentRatingFilter;
import com.chernoivan.books.rating.domain.AssessmentRating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRatingRepositoryCustom {
    List<AssessmentRating> findByFilter(AssessmentRatingFilter filter);
}
