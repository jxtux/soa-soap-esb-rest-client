package com.antoni.soa.esb.dto;

import java.util.List;

public record GovernancePolicyDto(
        String nombre,
        String descripcion,
        List<String> reglas
) {}
