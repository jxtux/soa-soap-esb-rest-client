package com.antoni.soa.esb.service;

import com.antoni.soa.esb.dto.GovernancePolicyDto;
import com.antoni.soa.esb.dto.RouteDto;
import com.antoni.soa.esb.dto.ServiceCatalogDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovernanceService {
    public ServiceCatalogDto catalog() {
        return new ServiceCatalogDto(
                "SOA Football ESB",
                "1.0.0",
                "Cliente REST -> ESB REST -> servicios SOAP",
                List.of(
                        "Enrutamiento / routing por endpoint REST y operación SOAP",
                        "Transformación JSON REST a XML SOAP y XML SOAP a JSON REST",
                        "Mediación de protocolos REST/HTTP hacia SOAP/XML",
                        "Seguridad técnica por token Bearer y cabecera X-Client-Certificate",
                        "Exposición centralizada de servicios REST del dominio",
                        "Autenticación y autorización por token USER/ADMIN",
                        "Trazabilidad con X-Correlation-Id",
                        "Logs de entrada, salida, rutas y errores",
                        "Reintentos ante fallas temporales de servicios SOAP",
                        "Gobernanza mediante catálogo, políticas y auditoría"
                ),
                routes()
        );
    }

    public List<RouteDto> routes() {
        return List.of(
                new RouteDto("GET", "/api/esb/partidos", "partidos-service", "getAllPartidosRequest", "USER o ADMIN", "SOAP XML -> JSON"),
                new RouteDto("GET", "/api/esb/partidos/{id}/detalle", "partidos + selecciones + estadisticas", "getPartidoByIdRequest + getSeleccionByIdRequest + getEstadisticasByPartidoRequest", "USER o ADMIN", "orquestación de 3 SOAP -> JSON consolidado"),
                new RouteDto("GET", "/api/esb/selecciones/{id}/resumen", "selecciones + partidos + estadisticas", "getSeleccionByIdRequest + getPartidosBySeleccionRequest + getResumenSeleccionRequest", "USER o ADMIN", "orquestación SOA -> JSON resumen"),
                new RouteDto("GET", "/api/esb/grupos/{grupo}/tabla", "selecciones + estadisticas", "getSeleccionesByGrupoRequest + getResumenSeleccionRequest", "USER o ADMIN", "agregación y ordenamiento de tabla"),
                new RouteDto("GET", "/api/esb/governance/**", "ESB interno", "N/A", "solo ADMIN", "gobernanza y auditoría")
        );
    }

    public List<GovernancePolicyDto> policies() {
        return List.of(
                new GovernancePolicyDto("Seguridad técnica", "Todo endpoint de negocio exige token Bearer y cabecera técnica de certificado.", List.of(
                        "Authorization debe ser Bearer esb-token-2026 o Bearer esb-admin-token-2026",
                        "X-Client-Certificate debe coincidir con SOA-CLIENT-CERT-2026",
                        "Las rutas /api/esb/governance/** requieren token ADMIN"
                )),
                new GovernancePolicyDto("Trazabilidad", "Cada request debe tener un identificador de correlación.", List.of(
                        "Si el cliente envía X-Correlation-Id, el ESB lo conserva",
                        "Si no lo envía, el ESB lo genera automáticamente",
                        "El mismo correlationId se propaga hacia los servicios SOAP"
                )),
                new GovernancePolicyDto("Reintentos", "Los errores temporales hacia SOAP son reintentados.", List.of(
                        "Máximo 3 intentos por defecto",
                        "Backoff de 500 ms por defecto",
                        "Si el servicio SOAP sigue fallando, el ESB devuelve 502 BAD_GATEWAY"
                )),
                new GovernancePolicyDto("Contratos", "El ESB expone contrato REST y consume contratos SOAP.", List.of(
                        "Los microservicios SOAP conservan su contrato WSDL",
                        "El ESB transforma XML SOAP a DTO JSON",
                        "El cliente REST no conoce directamente los servicios SOAP internos"
                ))
        );
    }
}
