package com.chernoivan.books.rating.dto.Author;

import lombok.Data;

import java.util.Date;

@Data
public class AuthorCreateDTO {
    private String firstName;
    private String lastName;
    private String biography;
    private Date dateOfBirth;
    private Double authorRating;
}
