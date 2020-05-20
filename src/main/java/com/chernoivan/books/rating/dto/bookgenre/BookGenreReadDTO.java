package com.chernoivan.books.rating.dto.bookgenre;

import com.chernoivan.books.rating.domain.enums.BookGenres;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class BookGenreReadDTO {
    private UUID id;
    private BookGenres bookGenres;
    private UUID bookId;
    private Instant createdAt;
    private Instant updatedAt;
}
