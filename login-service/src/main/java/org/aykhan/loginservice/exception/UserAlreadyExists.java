package org.aykhan.loginservice.exception;

public class UserAlreadyExists extends BadRequest {
    public UserAlreadyExists() {
        super("This username is taken");
    }
}
