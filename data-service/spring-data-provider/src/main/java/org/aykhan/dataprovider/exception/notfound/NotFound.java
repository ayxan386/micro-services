package org.aykhan.dataprovider.exception.notfound;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(value = NOT_FOUND)
public class NotFound extends RuntimeException {
  public NotFound(String message) {
    super(message);
  }
}
