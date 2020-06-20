package org.aykhan.springdataprovidermongo.exception.notfound;

public class UserNotFound extends NotFound {
    public UserNotFound() {
        super("No such user exists");
    }
}
