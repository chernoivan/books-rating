package com.chernoivan.movie.ratings.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
public class CreativeTeam {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String biography;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "creativeTeam", cascade = CascadeType.PERSIST)
    private List<MemberType> memberType;
}
