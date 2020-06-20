package org.aykhan.springdataprovidermongo.exception.notfound;

public class TokenNotFoundException extends NotFound {

    public TokenNotFoundException() {
        super("Token was not provided");
    }
}
