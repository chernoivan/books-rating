package com.chernoivan.books.rating.dto.book;

import com.chernoivan.books.rating.domain.enums.BookStatus;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
public class BookReadDTO {
    private UUID id;
    private String title;
    private Double bookRating;
    private Date releaseDate;
    private String info;
    private BookStatus bookStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
