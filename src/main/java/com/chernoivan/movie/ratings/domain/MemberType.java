package com.chernoivan.movie.ratings.domain;


import com.chernoivan.movie.ratings.domain.enums.CreativeMemberType;
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
public class MemberType {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CreativeMemberType creativeMemberType;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private CreativeTeam creativeTeam;

    @OneToMany(mappedBy = "memberType", cascade = CascadeType.PERSIST)
    private List<MemberAssessment> memberAssessments;

}
