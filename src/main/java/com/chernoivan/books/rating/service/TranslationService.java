package com.chernoivan.books.rating.service;

import com.chernoivan.books.rating.domain.*;
import com.chernoivan.books.rating.domain.enums.UserRoleType;
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
import com.chernoivan.books.rating.dto.userrole.UserRoleCreateDTO;
import com.chernoivan.books.rating.dto.userrole.UserRolePatchDTO;
import com.chernoivan.books.rating.dto.userrole.UserRolePutDTO;
import com.chernoivan.books.rating.dto.userrole.UserRoleReadDTO;
import com.chernoivan.books.rating.repository.RepositoryHelper;
import org.bitbucket.brunneng.ot.Configuration;
import org.bitbucket.brunneng.ot.ObjectTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TranslationService {

    @Autowired
    private RepositoryHelper repositoryHelper;

    private ObjectTranslator objectTranslator;

    public TranslationService() {
        objectTranslator = new ObjectTranslator(createConfiguration());
    }

    private Configuration createConfiguration() {
        Configuration c = new Configuration();
        configureForAssessment(c);
        configureForAssessmentRating(c);
        configureForBookGenre(c);

        return c;
    }

    private void configureForAssessment(Configuration c) {
        Configuration.Translation t = c.beanOfClass(Assessment.class).translationTo(AssessmentReadDTO.class);
        t.srcProperty("user.id").translatesTo("userId");
        t.srcProperty("book.id").translatesTo("bookId");
    }

    private void configureForAssessmentRating(Configuration c) {
        Configuration.Translation t = c.beanOfClass(AssessmentRating.class)
                .translationTo(AssessmentRatingReadDTO.class);
        t.srcProperty("user.id").translatesTo("userId");
        t.srcProperty("assessment.id").translatesTo("assessmentId");
    }

    private void configureForBookGenre(Configuration c) {
        Configuration.Translation t = c.beanOfClass(BookGenre.class).translationTo(BookGenreReadDTO.class);
        t.srcProperty("book.id").translatesTo("bookId");
    }

    public AssessmentRatingReadExtendedDTO toReadExtended(AssessmentRating assessmentRating) {
        AssessmentRatingReadExtendedDTO dto = new AssessmentRatingReadExtendedDTO();
        dto.setId(assessmentRating.getId());
        dto.setUserId(toRead(assessmentRating.getUser()));
        dto.setLikeStatus(assessmentRating.getLikeStatus());
        dto.setCreatedAt(assessmentRating.getCreatedAt());
        dto.setUpdatedAt(assessmentRating.getUpdatedAt());
        return dto;
    }

    public ApplicationUserReadDTO toRead(ApplicationUser applicationUser) {
        return objectTranslator.translate(applicationUser, ApplicationUserReadDTO.class);
    }

    public AssessmentReadDTO toRead(Assessment assessment) {
        return objectTranslator.translate(assessment, AssessmentReadDTO.class);
    }

    public AssessmentRatingReadDTO toRead(AssessmentRating assessmentRating) {
        return objectTranslator.translate(assessmentRating, AssessmentRatingReadDTO.class);
    }

    public BookReadDTO toRead(Book book) {
        return objectTranslator.translate(book, BookReadDTO.class);
    }

    public BookGenreReadDTO toRead(BookGenre bookGenre) {
        return objectTranslator.translate(bookGenre, BookGenreReadDTO.class);
    }

    public AuthorReadDTO toRead(Author author) {
        return objectTranslator.translate(author, AuthorReadDTO.class);
    }

    public UserRoleReadDTO toRead(UserRole userRole) {
        return objectTranslator.translate(userRole, UserRoleReadDTO.class);
    }

    public ApplicationUser toEntity(ApplicationUserCreateDTO create) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(create.getUsername());
        applicationUser.setEmail(create.getEmail());
        applicationUser.setAccess(create.getAccess());
        return applicationUser;
    }

    public Assessment toEntity(AssessmentCreateDTO create) {
        Assessment assessment = new Assessment();
        assessment.setAssessmentText(create.getAssessmentText());
        assessment.setAssessmentType(create.getAssessmentType());
        assessment.setLikesCount(create.getLikesCount());
        assessment.setRating(create.getRating());
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

    public UserRole toEntity(UserRoleCreateDTO create) {
        UserRole userRole = new UserRole();
        userRole.setUserType(UserRoleType.REGISTERED_USER);
        return userRole;
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
        if (patch.getBookGenres() != null) {
            bookGenre.setBookGenres(patch.getBookGenres());
        }
    }

    public void patchEntity(UserRolePatchDTO patch, UserRole userRole) {
        if (patch.getUserType() != null) {
            userRole.setUserType(patch.getUserType());
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
    }

    public void updateEntity(AssessmentPutDTO put, Assessment assessment) {
        assessment.setAssessmentText(put.getAssessmentText());
        assessment.setAssessmentType(put.getAssessmentType());
        assessment.setLikesCount(put.getLikesCount());
        assessment.setRating(put.getRating());
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
    }

    public void updateEntity(AuthorPutDTO put, Author author) {
        author.setAuthorRating(put.getAuthorRating());
        author.setBiography(put.getBiography());
        author.setDateOfBirth(put.getDateOfBirth());
        author.setFirstName(put.getFirstName());
        author.setLastName(put.getLastName());
    }

    public void updateEntity(UserRolePutDTO put, UserRole userRole) {
        userRole.setUserType(put.getUserType());
    }

}
