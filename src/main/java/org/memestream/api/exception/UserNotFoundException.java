package org.memestream.api.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class UserNotFoundException extends BaseException{
    public UserNotFoundException(String message) {
        var errorResponse = new ErrorResponse(
                "NotFound",
                message,
                HttpStatus.NOT_FOUND.value(),
                Instant.now()
        );
        super(HttpStatus.NOT_FOUND, errorResponse);
    }
}
