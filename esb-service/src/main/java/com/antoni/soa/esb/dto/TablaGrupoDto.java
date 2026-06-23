package com.antoni.soa.esb.dto;

import java.util.List;

public record TablaGrupoDto(
        String grupo,
        List<TablaGrupoItemDto> tabla,
        TraceDto trazabilidad
) {}
