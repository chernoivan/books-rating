package com.chernoivan.movie.ratings.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class MemberAssessment {
    @Id
    @GeneratedValue
    private UUID id;
    private String role;
    private Double rating;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "memberAssessment", cascade = CascadeType.PERSIST)
    private List<Assessment> items;

    @ManyToOne
    private Film film;

    @ManyToOne
    private MemberType memberType;

}
