package com.antoni.soa.esb.dto;

import java.util.List;

public record ServiceCatalogDto(
        String nombre,
        String version,
        String estiloIntegracion,
        List<String> capacidadesEsb,
        List<RouteDto> rutas
) {}
