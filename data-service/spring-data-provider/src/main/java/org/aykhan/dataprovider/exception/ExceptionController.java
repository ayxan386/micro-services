package org.aykhan.dataprovider.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.aykhan.dataprovider.dto.ErrorResponseDTO;
import org.aykhan.dataprovider.exception.badrequest.BadRequest;
import org.aykhan.dataprovider.exception.notfound.NotFound;
import org.aykhan.dataprovider.exception.unauth.UnAuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.*;

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

  @ExceptionHandler(value = UnAuthException.class)
  ResponseEntity<ErrorResponseDTO> unAuth(UnAuthException e) {
    ErrorResponseDTO res = ErrorResponseDTO
        .builder()
        .message(e.getMessage())
        .status(401)
        .build();
    return ResponseEntity
        .status(UNAUTHORIZED)
        .body(res);
  }

  @ExceptionHandler(value = BadRequest.class)
  ResponseEntity<ErrorResponseDTO> badRequest(BadRequest e) {
    ErrorResponseDTO res = ErrorResponseDTO
        .builder()
        .message(e.getMessage())
        .status(400)
        .build();
    return ResponseEntity
        .status(BAD_REQUEST)
        .body(res);
  }

  @ExceptionHandler(value = ExpiredJwtException.class)
  ResponseEntity<ErrorResponseDTO> expiredToken(ExpiredJwtException e) {
    ErrorResponseDTO res = ErrorResponseDTO.builder()
        .message("JWT token expired")
        .status(403).build();
    return ResponseEntity
        .status(403)
        .body(res);
  }
}
