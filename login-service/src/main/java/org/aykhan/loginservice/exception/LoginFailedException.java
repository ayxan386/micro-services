package org.aykhan.loginservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class LoginFailedException extends BadRequest {
  public LoginFailedException() {
    super("Error occurred during login process");
  }
}
