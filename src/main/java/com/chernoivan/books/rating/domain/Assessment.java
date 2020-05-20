package com.chernoivan.books.rating.domain;


import com.chernoivan.books.rating.domain.enums.AssessmentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Assessment {
    @Id
    @GeneratedValue
    private UUID id;
    private Integer likesCount;
    private String assessmentText;
    private Integer rating;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Enumerated(EnumType.STRING)
    private AssessmentType assessmentType;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.PERSIST)
    private List<AssessmentRating> items;

    @ManyToOne
    private Book book;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private ApplicationUser user;
}
