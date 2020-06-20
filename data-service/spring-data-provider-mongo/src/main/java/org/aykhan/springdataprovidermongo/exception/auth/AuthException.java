package org.aykhan.springdataprovidermongo.exception.auth;

public class AuthException extends RuntimeException {
    public AuthException() {
        super("Login problem");
    }
}
