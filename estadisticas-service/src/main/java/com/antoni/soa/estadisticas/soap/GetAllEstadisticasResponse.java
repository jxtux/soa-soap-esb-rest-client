package com.antoni.soa.estadisticas.soap;

import com.antoni.soa.estadisticas.model.Estadistica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "getAllEstadisticasResponse", namespace = "http://soa.antoni.com/estadisticas")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllEstadisticasResponse {
    @XmlElementWrapper(name = "estadisticas")
    @XmlElement(name = "estadistica")
    private List<Estadistica> estadisticas = new ArrayList<>();

    public GetAllEstadisticasResponse() {}
    public GetAllEstadisticasResponse(List<Estadistica> estadisticas) { this.estadisticas = estadisticas; }
    public List<Estadistica> getEstadisticas() { return estadisticas; }
    public void setEstadisticas(List<Estadistica> estadisticas) { this.estadisticas = estadisticas; }
}
