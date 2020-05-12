package com.chernoivan.movie.ratings.controller;

import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreCreateDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePatchDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePutDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreReadDTO;
import com.chernoivan.movie.ratings.service.FilmGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/film-genres")
public class FilmGenreController {
    @Autowired
    private FilmGenreService filmGenreService;

    @GetMapping("/{id}")
    public FilmGenreReadDTO getFilmGenre(@PathVariable UUID id) {
        return filmGenreService.getFilmGenre(id);
    }

    @PutMapping("/{id}")
    public FilmGenreReadDTO putFilmGenre(@PathVariable UUID id, @RequestBody FilmGenrePutDTO put) {
        return filmGenreService.updateFilmGenre(id, put);
    }

    @PostMapping
    public FilmGenreReadDTO createFilmGenre(@RequestBody FilmGenreCreateDTO create) {
        return filmGenreService.createFilmGenre(create);
    }

    @PatchMapping("/{id}")
    public FilmGenreReadDTO patchFilmGenre(@PathVariable UUID id, @RequestBody FilmGenrePatchDTO patch) {
        return filmGenreService.patchFilmGenre(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteFilmGenre(@PathVariable UUID id) {
        filmGenreService.deleteFilmGenre(id);
    }
}
