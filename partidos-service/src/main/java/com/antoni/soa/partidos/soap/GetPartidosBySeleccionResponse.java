package com.antoni.soa.partidos.soap;

import com.antoni.soa.partidos.model.Partido;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "getPartidosBySeleccionResponse", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPartidosBySeleccionResponse {
    @XmlElementWrapper(name = "partidos")
    @XmlElement(name = "partido")
    private List<Partido> partidos = new ArrayList<>();

    public GetPartidosBySeleccionResponse() {}
    public GetPartidosBySeleccionResponse(List<Partido> partidos) { this.partidos = partidos; }
    public List<Partido> getPartidos() { return partidos; }
    public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }
}
