package com.chernoivan.books.rating.domain;

import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ApplicationUser {

    @Id
    @GeneratedValue
    private UUID id;
    private String username;
    private String email;
    private String encodedPassword;

    @Enumerated(EnumType.STRING)
    private AccessLevelType access;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<AssessmentRating> items;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Assessment> assessments;

    @ManyToMany
    @JoinTable(name = "user_user_role",
            joinColumns = @JoinColumn(name = "application_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UserRole> userRoles = new ArrayList<>();
}
