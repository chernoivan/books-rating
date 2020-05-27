package com.chernoivan.books.rating.domain;

import com.chernoivan.books.rating.domain.enums.BookStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private Double bookRating;
    private LocalDate releaseDate;
    private String info;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<Assessment> items;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.PERSIST)
    private List<BookGenre> bookGenres;

    @ManyToMany(mappedBy = "authorBooks")
    private List<Author> authors = new ArrayList<>();

}
