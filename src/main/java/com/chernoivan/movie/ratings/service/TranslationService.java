package com.chernoivan.movie.ratings.service;

import com.chernoivan.movie.ratings.domain.*;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.movie.ratings.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.assessment.AssessmentReadDTO;
import com.chernoivan.movie.ratings.dto.assessmentrating.AssessmentRatingReadDTO;
import com.chernoivan.movie.ratings.dto.assessmentrating.AssessmentRatingReadExtendedDTO;
import com.chernoivan.movie.ratings.dto.film.FilmCreateDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPatchDTO;
import com.chernoivan.movie.ratings.dto.film.FilmPutDTO;
import com.chernoivan.movie.ratings.dto.film.FilmReadDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreCreateDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePatchDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenrePutDTO;
import com.chernoivan.movie.ratings.dto.filmgenre.FilmGenreReadDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentCreateDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPatchDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentPutDTO;
import com.chernoivan.movie.ratings.dto.memberassessment.MemberAssessmentReadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    @Autowired
    private ApplicationUserService applicationUserService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private MemberAssessmentService memberAssessmentService;

    @Autowired
    private MemberTypeService memberTypeService;

    public AssessmentRatingReadExtendedDTO toReadExtended(AssessmentRating assessmentRating) {
        AssessmentRatingReadExtendedDTO dto = new AssessmentRatingReadExtendedDTO();
        dto.setId(assessmentRating.getId());
        dto.setUser(toRead(assessmentRating.getUser()));
        dto.setLikeStatus(assessmentRating.getLikeStatus());
        return dto;
    }

    public ApplicationUserReadDTO toRead(ApplicationUser applicationUser) {
        ApplicationUserReadDTO dto = new ApplicationUserReadDTO();
        dto.setId(applicationUser.getId());
        dto.setUsername(applicationUser.getUsername());
        dto.setEmail(applicationUser.getEmail());
        dto.setCreatedAt(applicationUser.getCreatedAt());
        dto.setUpdatedAt(applicationUser.getUpdatedAt());
        dto.setAccess(applicationUser.getAccess());
        dto.setUserType(applicationUser.getUserType());
        return dto;
    }

    public AssessmentReadDTO toRead(Assessment assessment) {
        AssessmentReadDTO dto = new AssessmentReadDTO();
        dto.setId(assessment.getId());
        dto.setAssessmentText(assessment.getAssessmentText());
        dto.setAssessmentType(assessment.getAssessmentType());
        dto.setLikesCount(assessment.getLikesCount());
        dto.setRating(assessment.getRating());
        dto.setUser(assessment.getUser().getId());

        if (assessment.getFilm() != null) {
            dto.setFilm(assessment.getFilm().getId());
        }

        if (assessment.getMemberAssessment() != null) {
            dto.setMemberAssessment(assessment.getMemberAssessment().getId());
        }
        dto.setCreatedAt(assessment.getCreatedAt());
        dto.setUpdatedAt(assessment.getUpdatedAt());
        return dto;
    }

    public AssessmentRatingReadDTO toRead(AssessmentRating assessmentRating) {
        AssessmentRatingReadDTO dto = new AssessmentRatingReadDTO();
        dto.setId(assessmentRating.getId());
        dto.setLikeStatus(assessmentRating.getLikeStatus());
        dto.setAssessment(assessmentRating.getAssessment().getId());
        dto.setUser(assessmentRating.getUser().getId());
        dto.setCreatedAt(assessmentRating.getCreatedAt());
        dto.setUpdatedAt(assessmentRating.getUpdatedAt());
        return dto;
    }

    public FilmReadDTO toRead(Film film) {
        FilmReadDTO dto = new FilmReadDTO();
        dto.setId(film.getId());
        dto.setFilmRating(film.getFilmRating());
        dto.setFilmStatus(film.getFilmStatus());
        dto.setInfo(film.getInfo());
        dto.setReleaseDate(film.getReleaseDate());
        dto.setTitle(film.getTitle());
        dto.setCreatedAt(film.getCreatedAt());
        dto.setUpdatedAt(film.getUpdatedAt());
        return dto;
    }

    public MemberAssessmentReadDTO toRead(MemberAssessment memberAssessment) {
        MemberAssessmentReadDTO dto = new MemberAssessmentReadDTO();
        dto.setId(memberAssessment.getId());
        dto.setRole(memberAssessment.getRole());
        dto.setRating(memberAssessment.getRating());
        dto.setFilm(memberAssessment.getFilm().getId());
        dto.setMemberType(memberAssessment.getMemberType().getId());
        dto.setCreatedAt(memberAssessment.getCreatedAt());
        dto.setUpdatedAt(memberAssessment.getUpdatedAt());
        return dto;
    }

    public FilmGenreReadDTO toRead(FilmGenre filmGenre) {
        FilmGenreReadDTO dto = new FilmGenreReadDTO();
        dto.setId(filmGenre.getId());
        dto.setFilm(filmGenre.getFilm().getId());
        dto.setFilmGenres(filmGenre.getFilmGenres());
        dto.setCreatedAt(filmGenre.getCreatedAt());
        dto.setUpdatedAt(filmGenre.getUpdatedAt());
        return dto;
    }

    public ApplicationUser toEntity(ApplicationUserCreateDTO create) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(create.getUsername());
        applicationUser.setEmail(create.getEmail());
        applicationUser.setAccess(create.getAccess());
        applicationUser.setUserType(create.getUserType());
        return applicationUser;
    }

    public Assessment toEntity(AssessmentCreateDTO create) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText(create.getAssessmentText());
        assessment.setAssessmentType(create.getAssessmentType());
        assessment.setLikesCount(create.getLikesCount());
        assessment.setRating(create.getRating());
        assessment.setUser(applicationUserService.getUserRequired(create.getUser()));

        if (create.getFilm() != null) {
            assessment.setFilm(filmService.getFilmRequired(create.getFilm()));
        }

        if (create.getMemberAssessment() != null) {
            assessment.setMemberAssessment(memberAssessmentService.getMemberRequired(create.getMemberAssessment()));
        }

        return assessment;
    }

    public Film toEntity(FilmCreateDTO create) {
        Film film = new Film();
        film.setFilmRating(create.getFilmRating());
        film.setFilmStatus(create.getFilmStatus());
        film.setInfo(create.getInfo());
        film.setReleaseDate(create.getReleaseDate());
        film.setTitle(create.getTitle());
        return film;
    }

    public MemberAssessment toEntity(MemberAssessmentCreateDTO create) {
        MemberAssessment memberAssessment = new MemberAssessment();
        memberAssessment.setRole(create.getRole());
        memberAssessment.setRating(create.getRating());
        memberAssessment.setFilm(filmService.getFilmRequired(create.getFilm()));
        memberAssessment.setMemberType(memberTypeService.getMemberTypeRequired(create.getMemberType()));
        return memberAssessment;
    }

    public FilmGenre toEntity(FilmGenreCreateDTO create) {
        FilmGenre filmGenre = new FilmGenre();
        filmGenre.setFilmGenres(create.getFilmGenres());
        filmGenre.setFilm(filmService.getFilmRequired(create.getFilm()));
        return filmGenre;
    }

    public void patchEntity(ApplicationUserPatchDTO patch, ApplicationUser applicationUser) {
        if (patch.getUsername() != null) {
            applicationUser.setUsername(patch.getUsername());
        }

        if (patch.getEmail() != null) {
            applicationUser.setEmail(patch.getEmail());
        }

        if (patch.getAccess() != null) {
            applicationUser.setAccess(patch.getAccess());
        }

        if (patch.getUserType() != null) {
            applicationUser.setUserType(patch.getUserType());
        }
    }

    public void patchEntity(AssessmentPatchDTO patch, Assessment assessment) {
        if (patch.getAssessmentText() != null) {
            assessment.setAssessmentText(patch.getAssessmentText());
        }

        if (patch.getAssessmentType() != null) {
            assessment.setAssessmentType(patch.getAssessmentType());
        }

        if (patch.getLikesCount() != null) {
            assessment.setLikesCount(patch.getLikesCount());
        }

        if (patch.getRating() != null) {
            assessment.setRating(patch.getRating());
        }

        if (patch.getUser() != null) {
            assessment.setUser(applicationUserService.getUserRequired(patch.getUser()));
        }

        if (patch.getMemberAssessment() != null) {
            assessment.setMemberAssessment(memberAssessmentService.getMemberRequired(patch.getMemberAssessment()));
        }

        if (patch.getFilm() != null) {
            assessment.setFilm(filmService.getFilmRequired(patch.getFilm()));
        }
    }

    public void patchEntity(FilmPatchDTO patch, Film film) {
        if (patch.getFilmRating() != null) {
            film.setFilmRating(patch.getFilmRating());
        }

        if (patch.getFilmStatus() != null) {
            film.setFilmStatus(patch.getFilmStatus());
        }

        if (patch.getInfo() != null) {
            film.setInfo(patch.getInfo());
        }

        if (patch.getReleaseDate() != null) {
            film.setReleaseDate(patch.getReleaseDate());
        }

        if (patch.getTitle() != null) {
            film.setTitle(patch.getTitle());
        }
    }

    public void patchEntity(MemberAssessmentPatchDTO patch, MemberAssessment memberAssessment) {
        if (patch.getRole() != null) {
            memberAssessment.setRole(patch.getRole());
        }

        if (patch.getRating() != null) {
            memberAssessment.setRating(patch.getRating());
        }

        if(patch.getFilm() != null) {
            memberAssessment.setFilm(filmService.getFilmRequired(patch.getFilm()));
        }

        if(patch.getMemberType() != null) {
            memberAssessment.setMemberType(memberTypeService.getMemberTypeRequired(patch.getMemberType()));
        }
    }

    public void patchEntity(FilmGenrePatchDTO patch, FilmGenre filmGenre) {
        if (patch.getFilm() != null) {
            filmGenre.setFilm(filmService.getFilmRequired(patch.getFilm()));
        }

        if (patch.getFilmGenres() != null) {
            filmGenre.setFilmGenres(patch.getFilmGenres());
        }
    }

    public void updateEntity(ApplicationUserPutDTO put, ApplicationUser applicationUser) {
        applicationUser.setUsername(put.getUsername());
        applicationUser.setEmail(put.getEmail());
        applicationUser.setAccess(put.getAccess());
        applicationUser.setUserType(put.getUserType());
    }

    public void updateEntity(AssessmentPutDTO put, Assessment assessment) {
        assessment.setAssessmentText(put.getAssessmentText());
        assessment.setAssessmentType(put.getAssessmentType());
        assessment.setLikesCount(put.getLikesCount());
        assessment.setRating(put.getRating());
        assessment.setUser(applicationUserService.getUserRequired(put.getUser()));

        if (put.getMemberAssessment() != null) {
            assessment.setMemberAssessment(memberAssessmentService.getMemberRequired(put.getMemberAssessment()));
        }

        if (put.getFilm() != null) {
            assessment.setFilm(filmService.getFilmRequired(put.getFilm()));
        }
    }

    public void updateEntity(FilmPutDTO put, Film film) {
        film.setTitle(put.getTitle());
        film.setInfo(put.getInfo());
        film.setFilmStatus(put.getFilmStatus());
        film.setReleaseDate(put.getReleaseDate());
        film.setFilmRating(put.getFilmRating());
    }

    public void updateEntity(MemberAssessmentPutDTO put, MemberAssessment memberAssessment) {
        memberAssessment.setRole(put.getRole());
        memberAssessment.setRating(put.getRating());
        memberAssessment.setFilm(filmService.getFilmRequired(put.getFilm()));
        memberAssessment.setMemberType(memberTypeService.getMemberTypeRequired(put.getMemberType()));
    }

    public void updateEntity(FilmGenrePutDTO put, FilmGenre filmGenre) {
        filmGenre.setFilmGenres(put.getFilmGenres());
        filmGenre.setFilm(filmService.getFilmRequired(put.getFilm()));
    }

}
