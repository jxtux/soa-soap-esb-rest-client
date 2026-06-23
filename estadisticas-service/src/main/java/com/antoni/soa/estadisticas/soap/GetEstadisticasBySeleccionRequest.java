package com.antoni.soa.estadisticas.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getEstadisticasBySeleccionRequest", namespace = "http://soa.antoni.com/estadisticas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetEstadisticasBySeleccionRequest {
    private String seleccionId;
    public GetEstadisticasBySeleccionRequest() {}
    public String getSeleccionId() { return seleccionId; }
    public void setSeleccionId(String seleccionId) { this.seleccionId = seleccionId; }
}
