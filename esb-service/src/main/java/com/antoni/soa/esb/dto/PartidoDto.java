package com.antoni.soa.esb.dto;

public record PartidoDto(
        String id,
        String fecha,
        String estadio,
        String ciudad,
        String grupo,
        String seleccionLocalId,
        String seleccionVisitanteId,
        Integer golesLocal,
        Integer golesVisitante,
        String estado
) {}
