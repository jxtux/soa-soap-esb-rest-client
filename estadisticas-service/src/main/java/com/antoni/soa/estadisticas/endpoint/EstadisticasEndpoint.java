package com.antoni.soa.estadisticas.endpoint;

import com.antoni.soa.estadisticas.repository.EstadisticasRepository;
import com.antoni.soa.estadisticas.soap.GetAllEstadisticasRequest;
import com.antoni.soa.estadisticas.soap.GetAllEstadisticasResponse;
import com.antoni.soa.estadisticas.soap.GetEstadisticasByPartidoRequest;
import com.antoni.soa.estadisticas.soap.GetEstadisticasByPartidoResponse;
import com.antoni.soa.estadisticas.soap.GetEstadisticasBySeleccionRequest;
import com.antoni.soa.estadisticas.soap.GetEstadisticasBySeleccionResponse;
import com.antoni.soa.estadisticas.soap.GetResumenSeleccionRequest;
import com.antoni.soa.estadisticas.soap.GetResumenSeleccionResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EstadisticasEndpoint {
    private static final String NAMESPACE_URI = "http://soa.antoni.com/estadisticas";
    private final EstadisticasRepository repository;

    public EstadisticasEndpoint(EstadisticasRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllEstadisticasRequest")
    @ResponsePayload
    public GetAllEstadisticasResponse getAllEstadisticas(@RequestPayload GetAllEstadisticasRequest request) {
        return new GetAllEstadisticasResponse(repository.findAll());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEstadisticasBySeleccionRequest")
    @ResponsePayload
    public GetEstadisticasBySeleccionResponse getEstadisticasBySeleccion(@RequestPayload GetEstadisticasBySeleccionRequest request) {
        return new GetEstadisticasBySeleccionResponse(repository.findBySeleccionId(request.getSeleccionId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEstadisticasByPartidoRequest")
    @ResponsePayload
    public GetEstadisticasByPartidoResponse getEstadisticasByPartido(@RequestPayload GetEstadisticasByPartidoRequest request) {
        return new GetEstadisticasByPartidoResponse(repository.findByPartidoId(request.getPartidoId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getResumenSeleccionRequest")
    @ResponsePayload
    public GetResumenSeleccionResponse getResumenSeleccion(@RequestPayload GetResumenSeleccionRequest request) {
        return new GetResumenSeleccionResponse(repository.resumenBySeleccionId(request.getSeleccionId()));
    }
}
