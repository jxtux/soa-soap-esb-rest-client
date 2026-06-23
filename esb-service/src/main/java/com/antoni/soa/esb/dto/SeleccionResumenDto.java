package com.antoni.soa.esb.dto;

import java.util.List;

public record SeleccionResumenDto(
        SeleccionDto seleccion,
        ResumenSeleccionDto resumen,
        List<PartidoDto> partidos,
        TraceDto trazabilidad
) {}
