package com.antoni.soa.partidos.endpoint;

import com.antoni.soa.partidos.repository.PartidosRepository;
import com.antoni.soa.partidos.soap.GetAllPartidosRequest;
import com.antoni.soa.partidos.soap.GetAllPartidosResponse;
import com.antoni.soa.partidos.soap.GetPartidoByIdRequest;
import com.antoni.soa.partidos.soap.GetPartidoByIdResponse;
import com.antoni.soa.partidos.soap.GetPartidosBySeleccionRequest;
import com.antoni.soa.partidos.soap.GetPartidosBySeleccionResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PartidosEndpoint {
    private static final String NAMESPACE_URI = "http://soa.antoni.com/partidos";
    private final PartidosRepository repository;

    public PartidosEndpoint(PartidosRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllPartidosRequest")
    @ResponsePayload
    public GetAllPartidosResponse getAllPartidos(@RequestPayload GetAllPartidosRequest request) {
        return new GetAllPartidosResponse(repository.findAll());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPartidoByIdRequest")
    @ResponsePayload
    public GetPartidoByIdResponse getPartidoById(@RequestPayload GetPartidoByIdRequest request) {
        return repository.findById(request.getId())
                .map(partido -> new GetPartidoByIdResponse(partido, "OK"))
                .orElseGet(() -> new GetPartidoByIdResponse(null, "No existe el partido con id " + request.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPartidosBySeleccionRequest")
    @ResponsePayload
    public GetPartidosBySeleccionResponse getPartidosBySeleccion(@RequestPayload GetPartidosBySeleccionRequest request) {
        return new GetPartidosBySeleccionResponse(repository.findBySeleccionId(request.getSeleccionId()));
    }
}
