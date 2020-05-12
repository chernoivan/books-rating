package com.chernoivan.movie.ratings.repository;

import com.chernoivan.movie.ratings.domain.Film;
import com.chernoivan.movie.ratings.domain.enums.FilmStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from film", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmRepositoryTest {
    @Autowired
    private FilmRepository filmRepository;

    @Test
    public void testSave() {
        Film f = new Film();
        f = filmRepository.save(f);
        assertNotNull(f.getId());
        assertTrue(filmRepository.findById(f.getId()).isPresent());
    }

    @Test
    public void testCreatedAtIsSet() {
        Film film = createFilm();

        Instant createdAtBeforeReload = film.getCreatedAt();
        Assert.assertNotNull(createdAtBeforeReload);
        film = filmRepository.findById(film.getId()).get();

        Instant createdAtAfterReload = film.getCreatedAt();
        Assert.assertNotNull(createdAtAfterReload);
        Assert.assertEquals(createdAtBeforeReload, createdAtAfterReload);
    }

    @Test
    public void testUpdatedAtIsSet() {
        Film film = createFilm();

        Instant updatedAtBeforeReload = film.getUpdatedAt();
        Assert.assertNotNull(updatedAtBeforeReload);
        film = filmRepository.findById(film.getId()).get();

        Instant updatedAtAfterReload = film.getUpdatedAt();
        Assert.assertNotNull(updatedAtAfterReload);
        Assert.assertEquals(updatedAtBeforeReload, updatedAtAfterReload);
    }

    private Film createFilm() {
        Film film = new Film();
        film.setFilmRating(5.0);
        film.setFilmStatus(FilmStatus.UNRELEASED);
        film.setInfo("bla bla bla");
        film.setTitle("Hello Word");
        return filmRepository.save(film);
    }
}
