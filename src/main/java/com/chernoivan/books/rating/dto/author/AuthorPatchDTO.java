package com.chernoivan.books.rating.dto.author;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorPatchDTO {
    private String firstName;
    private String lastName;
    private String biography;
    private Date dateOfBirth;
    private Double authorRating;
}
