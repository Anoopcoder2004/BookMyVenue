package com.bookmyvenue.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import com.bookmyvenue.common.response.ApiResponse;

    @RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<?>> handleResponseStatusException(ResponseStatusException ex) {

        ApiResponse<?> response = new ApiResponse<>(
                ex.getStatusCode().value(),
                ex.getReason(),
                null
        );

        return new ResponseEntity<>(response, ex.getStatusCode());
    }
}
    
