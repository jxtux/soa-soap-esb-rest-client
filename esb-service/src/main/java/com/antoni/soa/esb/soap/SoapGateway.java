package com.antoni.soa.esb.soap;

import com.antoni.soa.esb.config.EsbSettings;
import com.antoni.soa.esb.dto.EstadisticaDto;
import com.antoni.soa.esb.dto.PartidoDto;
import com.antoni.soa.esb.dto.ResumenSeleccionDto;
import com.antoni.soa.esb.dto.SeleccionDto;
import com.antoni.soa.esb.exception.DownstreamServiceException;
import com.antoni.soa.esb.util.CorrelationContext;
import com.antoni.soa.esb.util.XmlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

@Component
public class SoapGateway {
    private static final Logger log = LoggerFactory.getLogger(SoapGateway.class);

    private static final String PARTIDOS_NS = "http://soa.antoni.com/partidos";
    private static final String SELECCIONES_NS = "http://soa.antoni.com/selecciones";
    private static final String ESTADISTICAS_NS = "http://soa.antoni.com/estadisticas";

    private final EsbSettings settings;
    private final HttpClient httpClient;

    public SoapGateway(EsbSettings settings) {
        this.settings = settings;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(settings.getTimeoutSeconds()))
                .build();
    }

    public List<PartidoDto> getAllPartidos() {
        String response = call("partidos-service", settings.getPartidosUrl(), envelope("par", PARTIDOS_NS, "getAllPartidosRequest", ""));
        return parsePartidos(response);
    }

    public PartidoDto getPartidoById(String id) {
        String body = "<id>" + XmlUtil.escape(id) + "</id>";
        String response = call("partidos-service", settings.getPartidosUrl(), envelope("par", PARTIDOS_NS, "getPartidoByIdRequest", body));
        return parsePartidos(response).stream().findFirst().orElse(null);
    }

    public List<PartidoDto> getPartidosBySeleccion(String seleccionId) {
        String body = "<seleccionId>" + XmlUtil.escape(seleccionId) + "</seleccionId>";
        String response = call("partidos-service", settings.getPartidosUrl(), envelope("par", PARTIDOS_NS, "getPartidosBySeleccionRequest", body));
        return parsePartidos(response);
    }

    public SeleccionDto getSeleccionById(String id) {
        String body = "<id>" + XmlUtil.escape(id) + "</id>";
        String response = call("selecciones-service", settings.getSeleccionesUrl(), envelope("sel", SELECCIONES_NS, "getSeleccionByIdRequest", body));
        return parseSelecciones(response).stream().findFirst().orElse(null);
    }

    public List<SeleccionDto> getSeleccionesByGrupo(String grupo) {
        String body = "<grupo>" + XmlUtil.escape(grupo) + "</grupo>";
        String response = call("selecciones-service", settings.getSeleccionesUrl(), envelope("sel", SELECCIONES_NS, "getSeleccionesByGrupoRequest", body));
        return parseSelecciones(response);
    }

    public List<EstadisticaDto> getEstadisticasByPartido(String partidoId) {
        String body = "<partidoId>" + XmlUtil.escape(partidoId) + "</partidoId>";
        String response = call("estadisticas-service", settings.getEstadisticasUrl(), envelope("est", ESTADISTICAS_NS, "getEstadisticasByPartidoRequest", body));
        return parseEstadisticas(response);
    }

    public List<EstadisticaDto> getEstadisticasBySeleccion(String seleccionId) {
        String body = "<seleccionId>" + XmlUtil.escape(seleccionId) + "</seleccionId>";
        String response = call("estadisticas-service", settings.getEstadisticasUrl(), envelope("est", ESTADISTICAS_NS, "getEstadisticasBySeleccionRequest", body));
        return parseEstadisticas(response);
    }

    public ResumenSeleccionDto getResumenSeleccion(String seleccionId) {
        String body = "<seleccionId>" + XmlUtil.escape(seleccionId) + "</seleccionId>";
        String response = call("estadisticas-service", settings.getEstadisticasUrl(), envelope("est", ESTADISTICAS_NS, "getResumenSeleccionRequest", body));
        Document document = XmlUtil.parse(response);
        return XmlUtil.elementsByLocalName(document, "resumen").stream()
                .findFirst()
                .map(this::toResumen)
                .orElse(null);
    }

    private String call(String serviceName, String url, String soapEnvelope) {
        DownstreamServiceException lastError = null;
        for (int attempt = 1; attempt <= settings.getMaxAttempts(); attempt++) {
            try {
                log.info("Routing SOAP service={} attempt={} url={}", serviceName, attempt, url);
                HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                        .timeout(Duration.ofSeconds(settings.getTimeoutSeconds()))
                        .header("Content-Type", "text/xml; charset=utf-8")
                        .header("X-Correlation-Id", CorrelationContext.get())
                        .POST(HttpRequest.BodyPublishers.ofString(soapEnvelope))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() >= 200 && response.statusCode() < 300) {
                    return response.body();
                }

                lastError = new DownstreamServiceException(serviceName, response.statusCode(), "HTTP " + response.statusCode() + " al invocar SOAP");
                if (response.statusCode() < 500) {
                    throw lastError;
                }
            } catch (DownstreamServiceException e) {
                lastError = e;
            } catch (Exception e) {
                lastError = new DownstreamServiceException(serviceName, 500, e.getMessage(), e);
            }

            if (attempt < settings.getMaxAttempts()) {
                try {
                    Thread.sleep(settings.getBackoffMs());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new DownstreamServiceException(serviceName, 500, "Reintento interrumpido", e);
                }
            }
        }
        throw lastError == null ? new DownstreamServiceException(serviceName, 500, "Error desconocido") : lastError;
    }

    private String envelope(String alias, String namespace, String operation, String innerBody) {
        return """
                <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:%s="%s">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <%s:%s>%s</%s:%s>
                  </soapenv:Body>
                </soapenv:Envelope>
                """.formatted(alias, namespace, alias, operation, innerBody, alias, operation);
    }

    private List<PartidoDto> parsePartidos(String xml) {
        Document document = XmlUtil.parse(xml);
        return XmlUtil.elementsByLocalName(document, "partido").stream().map(this::toPartido).toList();
    }

    private List<SeleccionDto> parseSelecciones(String xml) {
        Document document = XmlUtil.parse(xml);
        return XmlUtil.elementsByLocalName(document, "seleccion").stream().map(this::toSeleccion).toList();
    }

    private List<EstadisticaDto> parseEstadisticas(String xml) {
        Document document = XmlUtil.parse(xml);
        return XmlUtil.elementsByLocalName(document, "estadistica").stream().map(this::toEstadistica).toList();
    }

    private PartidoDto toPartido(Element element) {
        return new PartidoDto(
                XmlUtil.text(element, "id"),
                XmlUtil.text(element, "fecha"),
                XmlUtil.text(element, "estadio"),
                XmlUtil.text(element, "ciudad"),
                XmlUtil.text(element, "grupo"),
                XmlUtil.text(element, "seleccionLocalId"),
                XmlUtil.text(element, "seleccionVisitanteId"),
                XmlUtil.integer(element, "golesLocal"),
                XmlUtil.integer(element, "golesVisitante"),
                XmlUtil.text(element, "estado")
        );
    }

    private SeleccionDto toSeleccion(Element element) {
        return new SeleccionDto(
                XmlUtil.text(element, "id"),
                XmlUtil.text(element, "nombre"),
                XmlUtil.text(element, "confederacion"),
                XmlUtil.text(element, "grupo"),
                XmlUtil.integer(element, "rankingFifa"),
                XmlUtil.text(element, "entrenador")
        );
    }

    private EstadisticaDto toEstadistica(Element element) {
        return new EstadisticaDto(
                XmlUtil.text(element, "id"),
                XmlUtil.text(element, "partidoId"),
                XmlUtil.text(element, "seleccionId"),
                XmlUtil.integer(element, "golesFavor"),
                XmlUtil.integer(element, "golesContra"),
                XmlUtil.integer(element, "posesion"),
                XmlUtil.integer(element, "remates"),
                XmlUtil.integer(element, "faltas"),
                XmlUtil.integer(element, "puntos")
        );
    }

    private ResumenSeleccionDto toResumen(Element element) {
        return new ResumenSeleccionDto(
                XmlUtil.text(element, "seleccionId"),
                XmlUtil.integer(element, "partidosJugados"),
                XmlUtil.integer(element, "golesFavor"),
                XmlUtil.integer(element, "golesContra"),
                XmlUtil.integer(element, "diferenciaGoles"),
                XmlUtil.integer(element, "puntos"),
                XmlUtil.decimal(element, "posesionPromedio")
        );
    }
}
