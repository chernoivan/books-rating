package com.chernoivan.movie.ratings.dto.filmgenre;

import com.chernoivan.movie.ratings.domain.enums.FilmGenres;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FilmGenreReadDTO {
    private UUID id;
    private FilmGenres filmGenres;
    private UUID film;
    private Instant createdAt;
    private Instant updatedAt;
}
