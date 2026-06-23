package com.antoni.soa.esb.service;

import com.antoni.soa.esb.dto.*;
import com.antoni.soa.esb.exception.NotFoundException;
import com.antoni.soa.esb.soap.SoapGateway;
import com.antoni.soa.esb.util.CorrelationContext;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrchestrationService {
    private final SoapGateway soapGateway;

    public OrchestrationService(SoapGateway soapGateway) {
        this.soapGateway = soapGateway;
    }

    public List<PartidoDto> getPartidos() {
        return soapGateway.getAllPartidos();
    }

    public DetallePartidoDto getDetallePartido(String partidoId) {
        PartidoDto partido = soapGateway.getPartidoById(partidoId);
        if (partido == null) {
            throw new NotFoundException("No existe el partido " + partidoId);
        }

        SeleccionDto local = soapGateway.getSeleccionById(partido.seleccionLocalId());
        SeleccionDto visitante = soapGateway.getSeleccionById(partido.seleccionVisitanteId());
        List<EstadisticaDto> estadisticas = soapGateway.getEstadisticasByPartido(partidoId);

        return new DetallePartidoDto(
                partido,
                local,
                visitante,
                estadisticas,
                trace("detalle-partido", List.of(
                        "REST path parameter partidoId -> SOAP getPartidoByIdRequest.id",
                        "SOAP partido -> JSON partido",
                        "seleccionLocalId y seleccionVisitanteId -> SOAP getSeleccionByIdRequest",
                        "partidoId -> SOAP getEstadisticasByPartidoRequest",
                        "3 respuestas SOAP XML -> JSON consolidado"
                ), List.of("partidos-service", "selecciones-service", "estadisticas-service"))
        );
    }

    public SeleccionResumenDto getResumenSeleccion(String seleccionId) {
        SeleccionDto seleccion = soapGateway.getSeleccionById(seleccionId);
        if (seleccion == null) {
            throw new NotFoundException("No existe la selección " + seleccionId);
        }

        List<PartidoDto> partidos = soapGateway.getPartidosBySeleccion(seleccionId);
        ResumenSeleccionDto resumen = soapGateway.getResumenSeleccion(seleccionId);

        return new SeleccionResumenDto(
                seleccion,
                resumen,
                partidos,
                trace("resumen-seleccion", List.of(
                        "REST path parameter seleccionId -> SOAP getSeleccionByIdRequest.id",
                        "seleccionId -> SOAP getPartidosBySeleccionRequest",
                        "seleccionId -> SOAP getResumenSeleccionRequest",
                        "respuestas SOAP XML -> JSON resumen"
                ), List.of("selecciones-service", "partidos-service", "estadisticas-service"))
        );
    }

    public List<PartidoDto> getPartidosBySeleccion(String seleccionId) {
        return soapGateway.getPartidosBySeleccion(seleccionId);
    }

    public List<EstadisticaDto> getEstadisticasByPartido(String partidoId) {
        return soapGateway.getEstadisticasByPartido(partidoId);
    }

    public TablaGrupoDto getTablaGrupo(String grupo) {
        List<SeleccionDto> selecciones = soapGateway.getSeleccionesByGrupo(grupo);
        if (selecciones.isEmpty()) {
            throw new NotFoundException("No existen selecciones para el grupo " + grupo);
        }

        List<TablaGrupoItemDto> ordenada = selecciones.stream()
                .map(seleccion -> toTablaItem(seleccion, soapGateway.getResumenSeleccion(seleccion.id())))
                .sorted(Comparator
                        .comparing(TablaGrupoItemDto::puntos, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TablaGrupoItemDto::diferenciaGoles, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TablaGrupoItemDto::golesFavor, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(TablaGrupoItemDto::nombre))
                .toList();

        List<TablaGrupoItemDto> conPosicion = java.util.stream.IntStream.range(0, ordenada.size())
                .mapToObj(i -> new TablaGrupoItemDto(
                        i + 1,
                        ordenada.get(i).seleccionId(),
                        ordenada.get(i).nombre(),
                        ordenada.get(i).grupo(),
                        ordenada.get(i).partidosJugados(),
                        ordenada.get(i).golesFavor(),
                        ordenada.get(i).golesContra(),
                        ordenada.get(i).diferenciaGoles(),
                        ordenada.get(i).puntos(),
                        ordenada.get(i).posesionPromedio()
                ))
                .toList();

        return new TablaGrupoDto(
                grupo,
                conPosicion,
                trace("tabla-grupo", List.of(
                        "REST path parameter grupo -> SOAP getSeleccionesByGrupoRequest.grupo",
                        "cada seleccionId -> SOAP getResumenSeleccionRequest",
                        "XML SOAP -> JSON",
                        "regla ESB: ordenar por puntos, diferencia de goles y goles a favor"
                ), List.of("selecciones-service", "estadisticas-service"))
        );
    }

    private TablaGrupoItemDto toTablaItem(SeleccionDto seleccion, ResumenSeleccionDto resumen) {
        return new TablaGrupoItemDto(
                0,
                seleccion.id(),
                seleccion.nombre(),
                seleccion.grupo(),
                resumen == null ? 0 : resumen.partidosJugados(),
                resumen == null ? 0 : resumen.golesFavor(),
                resumen == null ? 0 : resumen.golesContra(),
                resumen == null ? 0 : resumen.diferenciaGoles(),
                resumen == null ? 0 : resumen.puntos(),
                resumen == null ? 0.0 : resumen.posesionPromedio()
        );
    }

    private TraceDto trace(String route, List<String> transformations, List<String> services) {
        return new TraceDto(
                CorrelationContext.get(),
                "REST/JSON",
                "SOAP/XML",
                route,
                transformations,
                services
        );
    }
}
