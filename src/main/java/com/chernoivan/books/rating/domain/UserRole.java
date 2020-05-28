package com.chernoivan.books.rating.domain;

import com.chernoivan.books.rating.domain.enums.UserRoleType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserRole {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private UserRoleType userType;

    @ManyToMany(mappedBy = "userRoles")
    private List<ApplicationUser> users = new ArrayList<>();
}
