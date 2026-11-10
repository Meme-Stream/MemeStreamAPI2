package org.memestream.api.exception;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {
    private String error;
    private String message;
    private int status;
    private Instant timestamp;
}
