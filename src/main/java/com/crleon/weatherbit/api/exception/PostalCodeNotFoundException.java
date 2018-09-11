package com.crleon.weatherbit.api.exception;

public class PostalCodeNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8084360582778818579L;

    public PostalCodeNotFoundException() {
        super();
    }

    public PostalCodeNotFoundException(String message) {
        super(message);
    }

    public PostalCodeNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
