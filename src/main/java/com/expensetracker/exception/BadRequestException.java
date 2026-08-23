package com.expensetracker.exception;

public class BadRequestExceptionHandler extends RuntimeException {

    public BadRequestExceptionHandler(String message) {
        super(message);
    }

}
