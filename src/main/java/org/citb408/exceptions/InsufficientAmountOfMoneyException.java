package org.citb408.exceptions;

public class InsufficientAmountOfMoneyException extends Exception {
    public InsufficientAmountOfMoneyException(String errorMessage) {
        super(errorMessage);
    }
}
