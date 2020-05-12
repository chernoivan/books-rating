package com.chernoivan.movie.ratings.dto.applicationuser;

import com.chernoivan.movie.ratings.domain.enums.AccessLevelType;
import com.chernoivan.movie.ratings.domain.enums.UserRoleType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApplicationUserReadDTO {
    private UUID id;
    private String username;
    private String email;
    private AccessLevelType access;
    private UserRoleType userType;
    private Instant createdAt;
    private Instant updatedAt;
}
