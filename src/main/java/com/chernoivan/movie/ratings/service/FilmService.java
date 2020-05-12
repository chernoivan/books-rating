package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.Film;
import com.chernoivan.movie.ratings.dto.film.FilmCreateDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPatchDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPutDTO;
import com.chernoivan.movie.ratings.dto.film.FilmReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TranslationService translationService;

    public FilmReadDTO getFilm(UUID id) {
        Film film = getFilmRequired(id);
        return translationService.toRead(film);
    }

    public FilmReadDTO createFilm(FilmCreateDTO create) {
        Film film = translationService.toEntity(create);

        film = filmRepository.save(film);
        return translationService.toRead(film);
    }

    public FilmReadDTO patchFilm(UUID id, FilmPatchDTO patch) {
        Film film = getFilmRequired(id);

        translationService.patchEntity(patch, film);

        film = filmRepository.save(film);
        return translationService.toRead(film);
    }


    public FilmReadDTO updateFilm(UUID id, FilmPutDTO put) {
        Film film = getFilmRequired(id);

        translationService.updateEntity(put, film);

        film = filmRepository.save(film);
        return translationService.toRead(film);
    }

    public void deleteUser(UUID id) {
        filmRepository.delete(getFilmRequired(id));
    }

    public Film getFilmRequired(UUID id) {
        return filmRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(Film.class, id));
    }
}
