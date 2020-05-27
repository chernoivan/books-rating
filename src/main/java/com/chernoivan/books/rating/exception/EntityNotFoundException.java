package com.chernoivan.books.rating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class entityClass, UUID id) {
        super(String.format("Entity %s with id=%s is not found", entityClass.getSimpleName(), id));
    }

    public EntityNotFoundException(Class entityClass, UUID id, Class entityClass1, UUID id1) {
        super(String.format("Entity %s with id=%s and entity %s with id=%s is not found",
                entityClass.getSimpleName(), id,
                entityClass1.getSimpleName(), id1));
    }
}
