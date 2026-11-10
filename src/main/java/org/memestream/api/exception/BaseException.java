package org.memestream.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorResponse errorResponse;
}
