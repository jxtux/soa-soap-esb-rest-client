package com.antoni.soa.esb.dto;

public record TablaGrupoItemDto(
        Integer posicion,
        String seleccionId,
        String nombre,
        String grupo,
        Integer partidosJugados,
        Integer golesFavor,
        Integer golesContra,
        Integer diferenciaGoles,
        Integer puntos,
        Double posesionPromedio
) {}
