package org.citb408.exceptions;

public class ExpirationDateReachedException extends Exception {
    public ExpirationDateReachedException(String errorMessage) {
        super(errorMessage);
    }
}
