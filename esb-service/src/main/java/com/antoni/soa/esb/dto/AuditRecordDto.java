package com.antoni.soa.esb.dto;

import java.time.Instant;

public record AuditRecordDto(
        Instant timestamp,
        String correlationId,
        String method,
        String path,
        int status,
        long durationMs,
        String principalRole
) {}
