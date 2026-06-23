package com.antoni.soa.esb.exception;

public class SecurityViolationException extends RuntimeException {
    private final int statusCode;

    public SecurityViolationException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() { return statusCode; }
}
