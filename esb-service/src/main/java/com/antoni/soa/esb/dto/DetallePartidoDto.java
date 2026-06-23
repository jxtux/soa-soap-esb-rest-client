package com.antoni.soa.esb.dto;

import java.util.List;

public record DetallePartidoDto(
        PartidoDto partido,
        SeleccionDto local,
        SeleccionDto visitante,
        List<EstadisticaDto> estadisticas,
        TraceDto trazabilidad
) {}
