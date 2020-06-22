package org.aykhan.dataprovider.exception.notfound;

public class UserNotFound extends NotFound {
  public UserNotFound() {
    super("No such user exists");
  }
}
