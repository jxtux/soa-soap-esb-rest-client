package com.antoni.soa.partidos.soap;

import com.antoni.soa.partidos.model.Partido;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "getAllPartidosResponse", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllPartidosResponse {
    @XmlElementWrapper(name = "partidos")
    @XmlElement(name = "partido")
    private List<Partido> partidos = new ArrayList<>();

    public GetAllPartidosResponse() {}
    public GetAllPartidosResponse(List<Partido> partidos) { this.partidos = partidos; }
    public List<Partido> getPartidos() { return partidos; }
    public void setPartidos(List<Partido> partidos) { this.partidos = partidos; }
}
