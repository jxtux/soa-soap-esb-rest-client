package com.antoni.soa.esb.dto;

public record ResumenSeleccionDto(
        String seleccionId,
        Integer partidosJugados,
        Integer golesFavor,
        Integer golesContra,
        Integer diferenciaGoles,
        Integer puntos,
        Double posesionPromedio
) {}
