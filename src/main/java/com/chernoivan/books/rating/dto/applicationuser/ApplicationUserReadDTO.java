package com.chernoivan.books.rating.dto.applicationuser;

import com.chernoivan.books.rating.domain.enums.AccessLevelType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ApplicationUserReadDTO {
    private UUID id;
    private String username;
    private String email;
    private String encodedPassword;
    private AccessLevelType access;
    private Instant createdAt;
    private Instant updatedAt;
}
