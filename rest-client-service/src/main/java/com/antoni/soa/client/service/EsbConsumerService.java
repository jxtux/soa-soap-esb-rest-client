package com.antoni.soa.client.service;

import com.antoni.soa.client.config.ClientSettings;
import com.antoni.soa.client.dto.ClientErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class EsbConsumerService {
    private static final Logger log = LoggerFactory.getLogger(EsbConsumerService.class);

    private final ClientSettings settings;
    private final ObjectMapper objectMapper;
    private final HttpClient httpClient;

    public EsbConsumerService(ClientSettings settings, ObjectMapper objectMapper) {
        this.settings = settings;
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(settings.getTimeoutSeconds()))
                .build();
    }

    public ResponseEntity<String> getPublic(String esbPath, String correlationId) {
        return execute(esbPath, null, false, correlationId);
    }

    public ResponseEntity<String> getAsUser(String esbPath, String correlationId) {
        return execute(esbPath, settings.getUserToken(), true, correlationId);
    }

    public ResponseEntity<String> getAsAdmin(String esbPath, String correlationId) {
        return execute(esbPath, settings.getAdminToken(), true, correlationId);
    }

    private ResponseEntity<String> execute(String esbPath, String token, boolean includeTechnicalSecurity, String correlationId) {
        String safeCorrelationId = normalizeCorrelationId(correlationId);
        String targetUrl = normalizeBaseUrl(settings.getEsbBaseUrl()) + esbPath;
        Exception lastException = null;

        for (int attempt = 1; attempt <= settings.getMaxAttempts(); attempt++) {
            try {
                log.info("Cliente REST consumiendo ESB path={} attempt={} correlationId={}", esbPath, attempt, safeCorrelationId);

                HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create(targetUrl))
                        .timeout(Duration.ofSeconds(settings.getTimeoutSeconds()))
                        .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                        .header("X-Correlation-Id", safeCorrelationId)
                        .GET();

                if (includeTechnicalSecurity) {
                    builder.header("Authorization", "Bearer " + token);
                    builder.header("X-Client-Certificate", settings.getClientCertificate());
                }

                HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
                int status = response.statusCode();
                String body = response.body() == null ? "" : response.body();

                if (status >= 500 && attempt < settings.getMaxAttempts()) {
                    sleepBackoff();
                    continue;
                }

                return ResponseEntity.status(status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Correlation-Id", safeCorrelationId)
                        .body(body);
            } catch (Exception e) {
                lastException = e;
                log.warn("Fallo al consumir ESB path={} attempt={} correlationId={} error={}", esbPath, attempt, safeCorrelationId, e.getMessage());
                if (attempt < settings.getMaxAttempts()) {
                    sleepBackoff();
                }
            }
        }

        return serviceUnavailable(esbPath, safeCorrelationId, lastException);
    }

    private ResponseEntity<String> serviceUnavailable(String path, String correlationId, Exception exception) {
        try {
            ClientErrorResponse error = new ClientErrorResponse(
                    Instant.now(),
                    HttpStatus.SERVICE_UNAVAILABLE.value(),
                    "ESB_UNAVAILABLE",
                    exception == null ? "No se pudo consumir el ESB" : exception.getMessage(),
                    correlationId,
                    path
            );
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Correlation-Id", correlationId)
                    .body(objectMapper.writeValueAsString(error));
        } catch (Exception serializationError) {
            String fallback = "{\"status\":503,\"error\":\"ESB_UNAVAILABLE\",\"message\":\"No se pudo consumir el ESB\"}";
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Correlation-Id", correlationId)
                    .body(fallback);
        }
    }

    private void sleepBackoff() {
        try {
            Thread.sleep(settings.getBackoffMs());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            return baseUrl.substring(0, baseUrl.length() - 1);
        }
        return baseUrl;
    }

    private String normalizeCorrelationId(String correlationId) {
        if (correlationId == null || correlationId.isBlank()) {
            return "client-" + UUID.randomUUID();
        }
        return correlationId;
    }
}
