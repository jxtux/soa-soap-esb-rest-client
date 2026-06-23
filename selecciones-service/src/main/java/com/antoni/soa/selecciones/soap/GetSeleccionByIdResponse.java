package com.antoni.soa.selecciones.soap;

import com.antoni.soa.selecciones.model.Seleccion;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getSeleccionByIdResponse", namespace = "http://soa.antoni.com/selecciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetSeleccionByIdResponse {
    private Seleccion seleccion;
    private String mensaje;

    public GetSeleccionByIdResponse() {}
    public GetSeleccionByIdResponse(Seleccion seleccion, String mensaje) {
        this.seleccion = seleccion;
        this.mensaje = mensaje;
    }
    public Seleccion getSeleccion() { return seleccion; }
    public void setSeleccion(Seleccion seleccion) { this.seleccion = seleccion; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
