package com.antoni.soa.esb.exception;

public class DownstreamServiceException extends RuntimeException {
    private final String service;
    private final int statusCode;

    public DownstreamServiceException(String service, int statusCode, String message) {
        super(message);
        this.service = service;
        this.statusCode = statusCode;
    }

    public DownstreamServiceException(String service, int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.service = service;
        this.statusCode = statusCode;
    }

    public String getService() { return service; }
    public int getStatusCode() { return statusCode; }
}
