package com.chernoivan.movie.ratings.controller;

import com.chernoivan.movie.ratings.dto.film.FilmCreateDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPatchDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPutDTO;
import com.chernoivan.movie.ratings.dto.film.FilmReadDTO;
import com.chernoivan.movie.ratings.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/films")
public class FilmController {

    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public FilmReadDTO getFilm(@PathVariable UUID id) {
        return filmService.getFilm(id);
    }

    @PutMapping("/{id}")
    public FilmReadDTO putFilm(@PathVariable UUID id, @RequestBody FilmPutDTO put) {
        return filmService.updateFilm(id, put);
    }

    @PostMapping
    public FilmReadDTO createFilm(@RequestBody FilmCreateDTO create) {
        return filmService.createFilm(create);
    }

    @PatchMapping("/{id}")
    public FilmReadDTO patchFilm(@PathVariable UUID id, @RequestBody FilmPatchDTO patch) {
        return filmService.patchFilm(id, patch);
    }

    @DeleteMapping("/{id}")
    public void deleteFilm(@PathVariable UUID id) {
        filmService.deleteUser(id);
    }
}
