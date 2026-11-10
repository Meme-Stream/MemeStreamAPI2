package org.memestream.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException authException) {
        var errorResponse = new ErrorResponse(
                "Unauthorized",
                authException.getMessage() != null ? authException.getMessage() : "Full authentication is required",
                HttpStatus.UNAUTHORIZED.value(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        var errorResponse = new ErrorResponse(
                "Forbidden",
                accessDeniedException.getMessage() != null ? accessDeniedException.getMessage() : "Full authentication is required",
                HttpStatus.FORBIDDEN.value(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleCustomBaseException(BaseException baseException) {
        return ResponseEntity.status(baseException.getStatus())
                .body(baseException.getErrorResponse());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException argumentException) {
        var errorResponse = new ErrorResponse(
                "Bad Request",
                argumentException.getMessage() != null ? argumentException.getMessage() : "Invalid input",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
