package com.antoni.soa.estadisticas.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getEstadisticasByPartidoRequest", namespace = "http://soa.antoni.com/estadisticas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEstadisticasByPartidoRequest {
    private String partidoId;
    public GetEstadisticasByPartidoRequest() {}
    public String getPartidoId() { return partidoId; }
    public void setPartidoId(String partidoId) { this.partidoId = partidoId; }
}
