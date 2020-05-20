package com.chernoivan.books.rating.repository;

import com.chernoivan.books.rating.dto.assessmentrating.AssessmentRatingFilter;
import com.chernoivan.books.rating.domain.AssessmentRating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

public class AssessmentRatingRepositoryCustomImpl implements AssessmentRatingRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AssessmentRating> findByFilter(AssessmentRatingFilter filter) {
        StringBuilder sb = new StringBuilder();
        sb.append("select v from AssessmentRating v where 1=1");
        if (filter.getLikeStatus() != null) {
            sb.append(" and v.likeStatus = :likeStatus");
        }
        if (filter.getAssessmentId() != null) {
            sb.append(" and v.assessment.id = :assessmentId");
        }
        if (filter.getUserId() != null) {
            sb.append(" and v.user.id = :userId");
        }
        TypedQuery<AssessmentRating> query = entityManager.createQuery(sb.toString(), AssessmentRating.class);

        if (filter.getUserId() != null) {
            query.setParameter("userId", filter.getUserId());
        }
        if (filter.getAssessmentId() != null) {
            query.setParameter("assessmentId", filter.getAssessmentId());
        }
        if (filter.getLikeStatus() != null) {
            query.setParameter("likeStatus", filter.getLikeStatus());
        }
        return query.getResultList();
    }
}
