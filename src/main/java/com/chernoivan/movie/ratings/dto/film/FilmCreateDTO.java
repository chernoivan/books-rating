package com.chernoivan.movie.ratings.dto.film;

import com.chernoivan.movie.ratings.domain.enums.FilmStatus;
import lombok.Data;

import java.util.Date;

@Data
public class FilmCreateDTO {
    private String title;
    private Double filmRating;
    private Date releaseDate;
    private String info;
    private FilmStatus filmStatus;
}
