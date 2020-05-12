package com.chernoivan.movie.ratings.domain;

import com.chernoivan.movie.ratings.domain.enums.FilmStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Film {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private Double filmRating;
    private Date releaseDate;
    private String info;

    @Enumerated(EnumType.STRING)
    private FilmStatus filmStatus;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST)
    private List<Assessment> items;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST)
    private List<MemberAssessment> memberAssessments;

    @OneToMany(mappedBy = "film", cascade = CascadeType.PERSIST)
    private List<FilmGenre> filmGenres;

}
