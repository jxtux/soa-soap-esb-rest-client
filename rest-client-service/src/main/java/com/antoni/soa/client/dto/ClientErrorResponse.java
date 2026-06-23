package com.antoni.soa.client.dto;

import java.time.Instant;

public record ClientErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String correlationId,
        String path
) {}
