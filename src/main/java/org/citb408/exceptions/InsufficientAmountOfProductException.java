package org.citb408.exceptions;

public class InsufficientAmountOfProductException extends Exception {
    public InsufficientAmountOfProductException(String errorMessage) {
        super(errorMessage);
    }
}
