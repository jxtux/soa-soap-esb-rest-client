package com.antoni.soa.esb.controller;

import com.antoni.soa.esb.config.EsbSettings;
import com.antoni.soa.esb.dto.*;
import com.antoni.soa.esb.service.GovernanceService;
import com.antoni.soa.esb.service.OrchestrationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/esb")
public class EsbController {
    private final OrchestrationService orchestrationService;
    private final GovernanceService governanceService;
    private final EsbSettings settings;

    public EsbController(OrchestrationService orchestrationService, GovernanceService governanceService, EsbSettings settings) {
        this.orchestrationService = orchestrationService;
        this.governanceService = governanceService;
        this.settings = settings;
    }

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "esb-service",
                "mensaje", "ESB listo para mediar REST hacia SOAP"
        );
    }

    @GetMapping("/auth/token")
    public TokenDto tokenInfo() {
        return new TokenDto(
                "Bearer",
                settings.getUserToken(),
                settings.getAdminToken(),
                "X-Client-Certificate",
                "Authorization: Bearer " + settings.getUserToken(),
                "X-Client-Certificate: " + settings.getClientCertificate()
        );
    }

    @GetMapping("/catalog")
    public ServiceCatalogDto catalog() {
        return governanceService.catalog();
    }

    @GetMapping("/partidos")
    public List<PartidoDto> partidos() {
        return orchestrationService.getPartidos();
    }

    @GetMapping("/partidos/{id}/detalle")
    public DetallePartidoDto detallePartido(@PathVariable String id) {
        return orchestrationService.getDetallePartido(id);
    }

    @GetMapping("/selecciones/{id}/resumen")
    public SeleccionResumenDto resumenSeleccion(@PathVariable String id) {
        return orchestrationService.getResumenSeleccion(id);
    }

    @GetMapping("/selecciones/{id}/partidos")
    public List<PartidoDto> partidosPorSeleccion(@PathVariable String id) {
        return orchestrationService.getPartidosBySeleccion(id);
    }

    @GetMapping("/estadisticas/partido/{id}")
    public List<EstadisticaDto> estadisticasPorPartido(@PathVariable String id) {
        return orchestrationService.getEstadisticasByPartido(id);
    }

    @GetMapping("/grupos/{grupo}/tabla")
    public TablaGrupoDto tablaGrupo(@PathVariable String grupo) {
        return orchestrationService.getTablaGrupo(grupo);
    }
}
