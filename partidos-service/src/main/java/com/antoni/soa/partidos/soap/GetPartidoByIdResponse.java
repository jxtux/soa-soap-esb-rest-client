package com.antoni.soa.partidos.soap;

import com.antoni.soa.partidos.model.Partido;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getPartidoByIdResponse", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPartidoByIdResponse {
    private Partido partido;
    private String mensaje;

    public GetPartidoByIdResponse() {}
    public GetPartidoByIdResponse(Partido partido, String mensaje) {
        this.partido = partido;
        this.mensaje = mensaje;
    }
    public Partido getPartido() { return partido; }
    public void setPartido(Partido partido) { this.partido = partido; }
    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
}
