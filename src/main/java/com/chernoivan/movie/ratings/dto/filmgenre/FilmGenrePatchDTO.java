package com.chernoivan.movie.ratings.dto.filmgenre;

import com.chernoivan.movie.ratings.domain.enums.FilmGenres;
import lombok.Data;

import java.util.UUID;

@Data
public class FilmGenrePatchDTO {
    private FilmGenres filmGenres;
    private UUID film;
}
