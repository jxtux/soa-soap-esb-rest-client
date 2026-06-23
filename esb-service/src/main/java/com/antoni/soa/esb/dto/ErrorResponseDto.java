package com.antoni.soa.esb.dto;

import java.time.Instant;

public record ErrorResponseDto(
        Instant timestamp,
        int status,
        String error,
        String message,
        String correlationId,
        String path
) {}
