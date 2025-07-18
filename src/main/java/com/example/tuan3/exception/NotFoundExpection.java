package com.example.tuan3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundExpection extends RuntimeException {
    public NotFoundExpection(String message) {
        super(message);
    }
}
