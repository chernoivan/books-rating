package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.FilmGenre;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreCreateDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePatchDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePutDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreReadDTO;
import com.chernoivan.movie.ratings.exception.EntityNotFoundException;
import com.chernoivan.movie.ratings.repository.FilmGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FilmGenreService {
    @Autowired
    private FilmGenreRepository filmGenreRepository;

    @Autowired
    private TranslationService translationService;

    public FilmGenreReadDTO getFilmGenre(UUID id) {
        FilmGenre filmGenre = getFilmGenreRequired(id);
        return translationService.toRead(filmGenre);
    }

    public FilmGenreReadDTO createFilmGenre(FilmGenreCreateDTO create) {
        FilmGenre filmGenre = translationService.toEntity(create);

        filmGenre = filmGenreRepository.save(filmGenre);
        return  translationService.toRead(filmGenre);
    }

    public FilmGenreReadDTO patchFilmGenre(UUID id, FilmGenrePatchDTO patch) {
        FilmGenre filmGenre = getFilmGenreRequired(id);

        translationService.patchEntity(patch, filmGenre);

        filmGenre = filmGenreRepository.save(filmGenre);
        return translationService.toRead(filmGenre);
    }

    public FilmGenreReadDTO updateFilmGenre(UUID id, FilmGenrePutDTO put) {
        FilmGenre filmGenre = getFilmGenreRequired(id);

        translationService.updateEntity(put, filmGenre);

        filmGenre = filmGenreRepository.save(filmGenre);
        return translationService.toRead(filmGenre);
    }

    public void deleteFilmGenre(UUID id) {
        filmGenreRepository.delete(getFilmGenreRequired(id));
    }

    private FilmGenre getFilmGenreRequired(UUID id) {
        return filmGenreRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(FilmGenre.class, id));
    }
}
