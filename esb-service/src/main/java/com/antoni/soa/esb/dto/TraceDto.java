package com.antoni.soa.esb.dto;

import java.util.List;

public record TraceDto(
        String correlationId,
        String entrada,
        String salida,
        String ruta,
        List<String> transformaciones,
        List<String> serviciosSoapInvocados
) {}
