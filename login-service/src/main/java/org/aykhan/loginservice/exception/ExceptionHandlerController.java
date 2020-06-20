package org.aykhan.loginservice.exception;

import org.aykhan.loginservice.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(BadRequest.class)
    ResponseEntity<ErrorDTO> badRequest(BadRequest e) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorDTO
                        .builder()
                        .message(e.getMessage())
                        .status(400)
                        .build());
    }
}
