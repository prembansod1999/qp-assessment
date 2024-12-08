package com.github.grocerybooking.exception;

public class InvalidInventoryUpdateException extends RuntimeException {

    public InvalidInventoryUpdateException(String message) {
        super(message);
    }

    public InvalidInventoryUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
