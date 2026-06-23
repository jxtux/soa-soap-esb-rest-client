package com.antoni.soa.partidos.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getPartidosBySeleccionRequest", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPartidosBySeleccionRequest {
    private String seleccionId;
    public GetPartidosBySeleccionRequest() {}
    public String getSeleccionId() { return seleccionId; }
    public void setSeleccionId(String seleccionId) { this.seleccionId = seleccionId; }
}
