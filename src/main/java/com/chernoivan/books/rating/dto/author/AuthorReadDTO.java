package com.chernoivan.books.rating.dto.author;

import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
public class AuthorReadDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String biography;
    private Date dateOfBirth;
    private Double authorRating;
    private Instant createdAt;
    private Instant updatedAt;
}
