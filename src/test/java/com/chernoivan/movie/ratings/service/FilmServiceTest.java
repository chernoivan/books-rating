package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.Film;
import com.chernoivan.movie.ratings.domain.enums.FilmStatus;
import com.chernoivan.movie.ratings.dto.film.FilmCreateDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPatchDTO;
import com.chernoivan.movie.ratings.dto.film.FilmReadDTO;
import com.chernoivan.movie.ratings.repository.FilmRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;
import org.testng.asserts.Assertion;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Sql(statements = "delete from film", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmServiceTest {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmService filmService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    public void testGetFilm() {
        Film film = createFilm();

        FilmReadDTO readDTO = filmService.getFilm(film.getId());
        Assertions.assertThat(readDTO).isEqualToComparingFieldByField(film);
    }

    @Test
    public void testCreateFilm() {
        FilmCreateDTO create = new FilmCreateDTO();
        create.setFilmRating(5.0);
        create.setFilmStatus(FilmStatus.UNRELEASED);
        create.setTitle("Hello");

        FilmReadDTO read = filmService.createFilm(create);
        Assertions.assertThat(create).isEqualToComparingFieldByField(read);
        Assert.assertNotNull(read.getId());

        Film film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(read).isEqualToComparingFieldByField(film);
    }

    @Test
    public void testPatchFilm() {
        Film film = createFilm();

        FilmPatchDTO patch = new FilmPatchDTO();
        patch.setFilmRating(5.0);
        patch.setInfo("bla bla bla");
        patch.setFilmStatus(FilmStatus.UNRELEASED);
        patch.setTitle("Hello");

        FilmReadDTO read = filmService.patchFilm(film.getId(), patch);

        Assertions.assertThat(patch).isEqualToComparingFieldByField(read);

        film = filmRepository.findById(read.getId()).get();
        Assertions.assertThat(film).isEqualToIgnoringGivenFields(read, "items", "memberAssessments", "filmGenres");

    }

    private Film createFilm() {
        Film film = new Film();
        film.setFilmRating(5.0);
        film.setFilmStatus(FilmStatus.UNRELEASED);
        film.setInfo("bla bla bla");
        film.setTitle("Hello Word");
        return filmRepository.save(film);
    }

    private void inTransaction(Runnable runnable) {
        transactionTemplate.executeWithoutResult(status -> {
            runnable.run();
        });
    }
}
