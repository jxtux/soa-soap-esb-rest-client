package com.antoni.soa.esb.dto;

public record RouteDto(
        String metodo,
        String endpointRest,
        String servicioSoapDestino,
        String operacionSoap,
        String seguridad,
        String transformacion
) {}
