package com.antoni.soa.esb.exception;

import com.antoni.soa.esb.dto.ErrorResponseDto;
import com.antoni.soa.esb.util.CorrelationContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NotFoundException ex, HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, "NOT_FOUND", ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(SecurityViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleSecurity(SecurityViolationException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode());
        return build(status, status.name(), ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(DownstreamServiceException.class)
    public ResponseEntity<ErrorResponseDto> handleDownstream(DownstreamServiceException ex, HttpServletRequest request) {
        return build(HttpStatus.BAD_GATEWAY, "DOWNSTREAM_SOAP_ERROR", ex.getService() + ": " + ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex, HttpServletRequest request) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ESB_ERROR", ex.getMessage(), request.getRequestURI());
    }

    private ResponseEntity<ErrorResponseDto> build(HttpStatus status, String error, String message, String path) {
        return ResponseEntity.status(status).body(new ErrorResponseDto(
                Instant.now(),
                status.value(),
                error,
                message,
                CorrelationContext.get(),
                path
        ));
    }
}
