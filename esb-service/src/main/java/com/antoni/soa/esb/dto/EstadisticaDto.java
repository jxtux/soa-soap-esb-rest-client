package com.antoni.soa.esb.dto;

public record EstadisticaDto(
        String id,
        String partidoId,
        String seleccionId,
        Integer golesFavor,
        Integer golesContra,
        Integer posesion,
        Integer remates,
        Integer faltas,
        Integer puntos
) {}
