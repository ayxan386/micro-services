package org.aykhan.dataprovider.exception.unauth;

public class InvalidTokenException extends UnAuthException {
  public InvalidTokenException() {
    super("Token expired!");
  }
}
