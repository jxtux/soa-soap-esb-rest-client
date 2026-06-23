package com.antoni.soa.esb.dto;

public record TokenDto(
        String tipo,
        String userToken,
        String adminToken,
        String clientCertificateHeader,
        String ejemploAuthorization,
        String ejemploCertificado
) {}
