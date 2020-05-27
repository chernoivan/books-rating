package com.chernoivan.books.rating.dto.book;

import com.chernoivan.books.rating.domain.enums.BookStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookPutDTO {
    private String title;
    private Double bookRating;
    private LocalDate releaseDate;
    private String info;
    private BookStatus bookStatus;

}
