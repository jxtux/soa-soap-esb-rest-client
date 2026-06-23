package com.antoni.soa.esb.filter;

import com.antoni.soa.esb.config.EsbSettings;
import com.antoni.soa.esb.dto.ErrorResponseDto;
import com.antoni.soa.esb.util.CorrelationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Set;

@Component
@Order(2)
public class TokenSecurityFilter extends OncePerRequestFilter {
    private final EsbSettings settings;
    private final ObjectMapper objectMapper;

    private static final Set<String> PUBLIC_PREFIXES = Set.of(
            "/actuator",
            "/api/esb/health",
            "/api/esb/auth/token"
    );

    public TokenSecurityFilter(EsbSettings settings, ObjectMapper objectMapper) {
        this.settings = settings;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!settings.isAuthEnabled() || isPublic(request.getRequestURI())) {
            request.setAttribute("principalRole", "PUBLIC");
            filterChain.doFilter(request, response);
            return;
        }

        String certificate = request.getHeader("X-Client-Certificate");
        if (!settings.getClientCertificate().equals(certificate)) {
            writeError(response, 401, "TECHNICAL_CERTIFICATE_REQUIRED", "Falta o no coincide X-Client-Certificate");
            return;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            writeError(response, 401, "TOKEN_REQUIRED", "Debe enviar Authorization: Bearer <token>");
            return;
        }

        String token = authorization.substring("Bearer ".length()).trim();
        String role;
        if (settings.getAdminToken().equals(token)) {
            role = "ADMIN";
        } else if (settings.getUserToken().equals(token)) {
            role = "USER";
        } else {
            writeError(response, 401, "TOKEN_INVALID", "Token inválido");
            return;
        }

        if (request.getRequestURI().startsWith("/api/esb/governance") && !"ADMIN".equals(role)) {
            writeError(response, 403, "FORBIDDEN", "Esta ruta requiere rol ADMIN");
            return;
        }

        request.setAttribute("principalRole", role);
        filterChain.doFilter(request, response);
    }

    private boolean isPublic(String path) {
        return PUBLIC_PREFIXES.stream().anyMatch(path::startsWith);
    }

    private void writeError(HttpServletResponse response, int status, String error, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), new ErrorResponseDto(
                Instant.now(),
                status,
                error,
                message,
                CorrelationContext.get(),
                null
        ));
    }
}
