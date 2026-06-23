package com.antoni.soa.client.dto;

public record ClientHealthResponse(
        String service,
        String status,
        String mensaje,
        int esbStatusCode,
        Object esb
) {}
