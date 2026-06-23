package com.antoni.soa.client.controller;

import com.antoni.soa.client.dto.ClientHealthResponse;
import com.antoni.soa.client.service.EsbConsumerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/client")
public class RestClientController {
    private final EsbConsumerService esbConsumerService;
    private final ObjectMapper objectMapper;

    public RestClientController(EsbConsumerService esbConsumerService, ObjectMapper objectMapper) {
        this.esbConsumerService = esbConsumerService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/health")
    public ResponseEntity<ClientHealthResponse> health(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        ResponseEntity<String> esbResponse = esbConsumerService.getPublic("/api/esb/health", correlationId);
        Object esbBody = parseOrRaw(esbResponse.getBody());
        ClientHealthResponse response = new ClientHealthResponse(
                "rest-client-service",
                esbResponse.getStatusCode().is2xxSuccessful() ? "UP" : "DEGRADED",
                "Cliente REST listo para consumir el ESB",
                esbResponse.getStatusCode().value(),
                esbBody
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/security/model")
    public Map<String, Object> securityModel() {
        return Map.of(
                "clienteRest", "No consume SOAP directamente; solo consume REST del ESB",
                "autenticacionTecnica", "El cliente REST envía Authorization Bearer hacia el ESB",
                "certificadoSimulado", "El cliente REST envía X-Client-Certificate hacia el ESB",
                "trazabilidad", "El cliente REST propaga o genera X-Correlation-Id",
                "roles", Map.of(
                        "USER", "Consulta catálogo, partidos, selecciones, estadísticas y tablas",
                        "ADMIN", "Consulta rutas, políticas y auditoría de gobernanza"
                )
        );
    }

    @GetMapping("/catalog")
    public ResponseEntity<String> catalog(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/catalog", correlationId);
    }

    @GetMapping("/partidos")
    public ResponseEntity<String> partidos(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/partidos", correlationId);
    }

    @GetMapping("/partidos/{id}/detalle")
    public ResponseEntity<String> detallePartido(@PathVariable String id,
                                                  @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/partidos/" + id + "/detalle", correlationId);
    }

    @GetMapping("/selecciones/{id}/resumen")
    public ResponseEntity<String> resumenSeleccion(@PathVariable String id,
                                                    @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/selecciones/" + id + "/resumen", correlationId);
    }

    @GetMapping("/selecciones/{id}/partidos")
    public ResponseEntity<String> partidosPorSeleccion(@PathVariable String id,
                                                        @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/selecciones/" + id + "/partidos", correlationId);
    }

    @GetMapping("/estadisticas/partido/{id}")
    public ResponseEntity<String> estadisticasPorPartido(@PathVariable String id,
                                                          @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/estadisticas/partido/" + id, correlationId);
    }

    @GetMapping("/grupos/{grupo}/tabla")
    public ResponseEntity<String> tablaGrupo(@PathVariable String grupo,
                                              @RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsUser("/api/esb/grupos/" + grupo + "/tabla", correlationId);
    }

    @GetMapping("/admin/governance/catalog")
    public ResponseEntity<String> governanceCatalog(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsAdmin("/api/esb/governance/catalog", correlationId);
    }

    @GetMapping("/admin/governance/routes")
    public ResponseEntity<String> governanceRoutes(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsAdmin("/api/esb/governance/routes", correlationId);
    }

    @GetMapping("/admin/governance/policies")
    public ResponseEntity<String> governancePolicies(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsAdmin("/api/esb/governance/policies", correlationId);
    }

    @GetMapping("/admin/governance/audits")
    public ResponseEntity<String> governanceAudits(@RequestHeader(value = "X-Correlation-Id", required = false) String correlationId) {
        return esbConsumerService.getAsAdmin("/api/esb/governance/audits", correlationId);
    }

    private Object parseOrRaw(String body) {
        if (body == null || body.isBlank()) {
            return null;
        }
        try {
            JsonNode node = objectMapper.readTree(body);
            return node;
        } catch (Exception e) {
            return body;
        }
    }
}
