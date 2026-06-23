package com.antoni.soa.estadisticas.soap;

import com.antoni.soa.estadisticas.model.ResumenSeleccion;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getResumenSeleccionResponse", namespace = "http://soa.antoni.com/estadisticas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetResumenSeleccionResponse {
    private ResumenSeleccion resumen;
    public GetResumenSeleccionResponse() {}
    public GetResumenSeleccionResponse(ResumenSeleccion resumen) { this.resumen = resumen; }
    public ResumenSeleccion getResumen() { return resumen; }
    public void setResumen(ResumenSeleccion resumen) { this.resumen = resumen; }
}
