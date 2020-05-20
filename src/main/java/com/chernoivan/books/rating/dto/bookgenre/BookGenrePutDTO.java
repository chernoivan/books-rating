package com.chernoivan.books.rating.dto.bookgenre;

import com.chernoivan.books.rating.domain.enums.BookGenres;
import lombok.Data;

import java.util.UUID;

@Data
public class BookGenrePutDTO {
    private BookGenres bookGenres;
    private UUID bookId;
}
