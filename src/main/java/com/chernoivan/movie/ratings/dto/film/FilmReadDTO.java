package com.chernoivan.movie.ratings.dto.film;

import com.chernoivan.movie.ratings.domain.enums.FilmStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
public class FilmReadDTO {
    private UUID id;
    private String title;
    private Double filmRating;
    private Date releaseDate;
    private String info;
    private FilmStatus filmStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
