package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.*;
import com.chernoivan.books.rating.dto.author.AuthorCreateDTO;
import com.chernoivan.books.rating.dto.author.AuthorPatchDTO;
import com.chernoivan.books.rating.dto.author.AuthorPutDTO;
import com.chernoivan.books.rating.dto.author.AuthorReadDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserCreateDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPatchDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserPutDTO;
import com.chernoivan.books.rating.dto.applicationuser.ApplicationUserReadDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentCreateDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPatchDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentPutDTO;
import com.chernoivan.books.rating.dto.assessment.AssessmentReadDTO;
import com.chernoivan.books.rating.dto.assessmentrating.AssessmentRatingReadDTO;
import com.chernoivan.books.rating.dto.assessmentrating.AssessmentRatingReadExtendedDTO;
import com.chernoivan.books.rating.dto.book.BookCreateDTO;
import com.chernoivan.books.rating.dto.book.BookPatchDTO;
import com.chernoivan.books.rating.dto.book.BookPutDTO;
import com.chernoivan.books.rating.dto.book.BookReadDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreCreateDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePatchDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenrePutDTO;
import com.chernoivan.books.rating.dto.bookgenre.BookGenreReadDTO;
import com.chernoivan.books.rating.repository.RepositoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repositoryHelper;

//    private ObjectTranslator objectTranslator = new ObjectTranslator();

//    public TranslationService() {
//        objectTranslator = new ObjectTranslator(createConfiguration());
//    }
//
//    private Configuration createConfiguration() {
//        Configuration c = new Configuration();
//        configureForAssessment(c);
//
//        return c;
//    }
//
//    private void configureForAssessment(Configuration c) {
//        Configuration.Translation t = c.beanOfClass(Assessment.class).translationTo(AssessmentReadDTO.class);
//        t.srcProperty("user.id").translatesTo("userId");
//    }

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
        dto.setUserId(assessment.getUser().getId());
        dto.setBookId(assessment.getBook().getId());
        dto.setCreatedAt(assessment.getCreatedAt());
        dto.setUpdatedAt(assessment.getUpdatedAt());
        return dto;
    }

//    public AssessmentReadDTO toRead(Assessment assessment) {
//        return objectTranslator.translate(assessment, AssessmentReadDTO.class);
//    }

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

    public BookReadDTO toRead(Book book) {
        BookReadDTO dto = new BookReadDTO();
        dto.setId(book.getId());
        dto.setBookRating(book.getBookRating());
        dto.setBookStatus(book.getBookStatus());
        dto.setInfo(book.getInfo());
        dto.setReleaseDate(book.getReleaseDate());
        dto.setTitle(book.getTitle());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        return dto;
    }

    public BookGenreReadDTO toRead(BookGenre bookGenre) {
        BookGenreReadDTO dto = new BookGenreReadDTO();
        dto.setId(bookGenre.getId());
        dto.setBookId(bookGenre.getBook().getId());
        dto.setBookGenres(bookGenre.getBookGenres());
        dto.setCreatedAt(bookGenre.getCreatedAt());
        dto.setUpdatedAt(bookGenre.getUpdatedAt());
        return dto;
    }

    public AuthorReadDTO toRead(Author author) {
        AuthorReadDTO dto = new AuthorReadDTO();
        dto.setId(author.getId());
        dto.setAuthorRating(author.getAuthorRating());
        dto.setBiography(author.getBiography());
        dto.setDateOfBirth(author.getDateOfBirth());
        dto.setFirstName(author.getFirstName());
        dto.setLastName(author.getLastName());
        dto.setCreatedAt(author.getCreatedAt());
        dto.setUpdatedAt(author.getUpdatedAt());
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
        assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, create.getUserId()));
        assessment.setBook(repositoryHelper.getReferenceIfExist(Book.class, create.getBookId()));
        return assessment;
    }

    public Book toEntity(BookCreateDTO create) {
        Book book = new Book();
        book.setBookRating(create.getBookRating());
        book.setBookStatus(create.getBookStatus());
        book.setInfo(create.getInfo());
        book.setReleaseDate(create.getReleaseDate());
        book.setTitle(create.getTitle());
        return book;
    }


    public BookGenre toEntity(BookGenreCreateDTO create) {
        BookGenre bookGenre = new BookGenre();
        bookGenre.setBookGenres(create.getBookGenres());
        bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, create.getBookId()));
        return bookGenre;
    }

    public Author toEntity(AuthorCreateDTO create) {
        Author author = new Author();
        author.setAuthorRating(create.getAuthorRating());
        author.setBiography(create.getBiography());
        author.setDateOfBirth(create.getDateOfBirth());
        author.setFirstName(create.getFirstName());
        author.setLastName(create.getLastName());
        return author;
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

        if (patch.getUserId() != null) {
            assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, patch.getUserId()));
        }

        if (patch.getBookId() != null) {
            assessment.setBook(repositoryHelper.getReferenceIfExist(Book.class, patch.getBookId()));
        }
    }

    public void patchEntity(BookPatchDTO patch, Book book) {
        if (patch.getBookRating() != null) {
            book.setBookRating(patch.getBookRating());
        }

        if (patch.getBookStatus() != null) {
            book.setBookStatus(patch.getBookStatus());
        }

        if (patch.getInfo() != null) {
            book.setInfo(patch.getInfo());
        }

        if (patch.getReleaseDate() != null) {
            book.setReleaseDate(patch.getReleaseDate());
        }

        if (patch.getTitle() != null) {
            book.setTitle(patch.getTitle());
        }
    }

    public void patchEntity(BookGenrePatchDTO patch, BookGenre bookGenre) {
        if (patch.getBookId() != null) {
            bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, patch.getBookId()));
        }

        if (patch.getBookGenres() != null) {
            bookGenre.setBookGenres(patch.getBookGenres());
        }
    }

    public void patchEntity(AuthorPatchDTO patch, Author author) {
        if (patch.getFirstName() != null) {
            author.setFirstName(patch.getFirstName());
        }

        if (patch.getLastName() != null) {
            author.setLastName(patch.getLastName());
        }

        if (patch.getAuthorRating() != null) {
            author.setAuthorRating(patch.getAuthorRating());
        }

        if (patch.getBiography() != null) {
            author.setBiography(patch.getBiography());
        }

        if (patch.getDateOfBirth() != null) {
            author.setDateOfBirth(patch.getDateOfBirth());
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
        assessment.setUser(repositoryHelper.getReferenceIfExist(ApplicationUser.class, put.getUserId()));
        assessment.setBook(repositoryHelper.getReferenceIfExist(Book.class, put.getBookId()));
    }

    public void updateEntity(BookPutDTO put, Book book) {
        book.setTitle(put.getTitle());
        book.setInfo(put.getInfo());
        book.setBookStatus(put.getBookStatus());
        book.setReleaseDate(put.getReleaseDate());
        book.setBookRating(put.getBookRating());
    }

    public void updateEntity(BookGenrePutDTO put, BookGenre bookGenre) {
        bookGenre.setBookGenres(put.getBookGenres());
        bookGenre.setBook(repositoryHelper.getReferenceIfExist(Book.class, put.getBookId()));
    }

    public void updateEntity(AuthorPutDTO put, Author author) {
        author.setAuthorRating(put.getAuthorRating());
        author.setBiography(put.getBiography());
        author.setDateOfBirth(put.getDateOfBirth());
        author.setFirstName(put.getFirstName());
        author.setLastName(put.getLastName());
    }

}
