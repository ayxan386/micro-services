package org.aykhan.loginservice.exception;

public class BadAuthException extends BadRequest {
    public BadAuthException() {
        super("Problem during Authentication");
    }
}
