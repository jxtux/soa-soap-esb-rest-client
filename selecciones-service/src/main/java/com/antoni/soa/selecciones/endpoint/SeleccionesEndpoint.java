package com.antoni.soa.selecciones.endpoint;

import com.antoni.soa.selecciones.repository.SeleccionesRepository;
import com.antoni.soa.selecciones.soap.GetAllSeleccionesRequest;
import com.antoni.soa.selecciones.soap.GetAllSeleccionesResponse;
import com.antoni.soa.selecciones.soap.GetSeleccionByIdRequest;
import com.antoni.soa.selecciones.soap.GetSeleccionByIdResponse;
import com.antoni.soa.selecciones.soap.GetSeleccionesByGrupoRequest;
import com.antoni.soa.selecciones.soap.GetSeleccionesByGrupoResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class SeleccionesEndpoint {
    private static final String NAMESPACE_URI = "http://soa.antoni.com/selecciones";
    private final SeleccionesRepository repository;

    public SeleccionesEndpoint(SeleccionesRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllSeleccionesRequest")
    @ResponsePayload
    public GetAllSeleccionesResponse getAllSelecciones(@RequestPayload GetAllSeleccionesRequest request) {
        return new GetAllSeleccionesResponse(repository.findAll());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSeleccionByIdRequest")
    @ResponsePayload
    public GetSeleccionByIdResponse getSeleccionById(@RequestPayload GetSeleccionByIdRequest request) {
        return repository.findById(request.getId())
                .map(seleccion -> new GetSeleccionByIdResponse(seleccion, "OK"))
                .orElseGet(() -> new GetSeleccionByIdResponse(null, "No existe la selección con id " + request.getId()));
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getSeleccionesByGrupoRequest")
    @ResponsePayload
    public GetSeleccionesByGrupoResponse getSeleccionesByGrupo(@RequestPayload GetSeleccionesByGrupoRequest request) {
        return new GetSeleccionesByGrupoResponse(repository.findByGrupo(request.getGrupo()));
    }
}
