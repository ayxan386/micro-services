package org.aykhan.dataprovider.exception.notfound;

public class TokenNotFoundException extends NotFound {

    public TokenNotFoundException() {
        super("Token was not provided");
    }
}
