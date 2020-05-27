package com.chernoivan.books.rating.dto.author;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorPutDTO {
    private String firstName;
    private String lastName;
    private String biography;
    private LocalDate dateOfBirth;
    private Double authorRating;
}
