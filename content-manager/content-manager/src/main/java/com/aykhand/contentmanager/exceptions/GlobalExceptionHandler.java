package com.aykhand.contentmanager.exceptions;

import com.aykhand.contentmanager.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(BadRequest.class)
  ResponseEntity<ErrorDTO> badRequest(BadRequest badRequest) {
    return ResponseEntity
        .status(400)
        .body(ErrorDTO
            .builder()
            .message(badRequest.getMessage())
            .status(400)
            .build());
  }

  @ExceptionHandler(NotFound.class)
  ResponseEntity<ErrorDTO> notFound(NotFound notFound) {
    return ResponseEntity
        .status(404)
        .body(ErrorDTO
            .builder()
            .message(notFound.getMessage())
            .status(404)
            .build());
  }
}
