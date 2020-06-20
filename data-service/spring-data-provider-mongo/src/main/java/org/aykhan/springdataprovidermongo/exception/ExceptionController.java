package org.aykhan.springdataprovidermongo.exception;

import org.aykhan.springdataprovidermongo.dto.ErrorResponseDTO;
import org.aykhan.springdataprovidermongo.exception.auth.AuthException;
import org.aykhan.springdataprovidermongo.exception.notfound.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = NotFound.class)
    ResponseEntity<ErrorResponseDTO> notFound(NotFound e) {
        ErrorResponseDTO res = ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .status(404)
                .build();
        return ResponseEntity
                .status(NOT_FOUND)
                .body(res);
    }

    @ExceptionHandler(value = AuthException.class)
    ResponseEntity<ErrorResponseDTO> authException(AuthException e) {
        ErrorResponseDTO res = ErrorResponseDTO
                .builder()
                .message(e.getMessage())
                .status(401)
                .build();
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(res);
    }
}
