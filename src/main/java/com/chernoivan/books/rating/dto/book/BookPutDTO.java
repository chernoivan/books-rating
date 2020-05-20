package com.chernoivan.books.rating.dto.book;

import com.chernoivan.books.rating.domain.enums.BookStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BookPutDTO {
    private String title;
    private Double bookRating;
    private Date releaseDate;
    private String info;
    private BookStatus bookStatus;

}
