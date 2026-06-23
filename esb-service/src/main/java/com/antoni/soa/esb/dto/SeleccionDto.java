package com.antoni.soa.esb.dto;

public record SeleccionDto(
        String id,
        String nombre,
        String confederacion,
        String grupo,
        Integer rankingFifa,
        String entrenador
) {}
