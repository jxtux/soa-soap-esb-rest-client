package com.antoni.soa.selecciones.soap;

import com.antoni.soa.selecciones.model.Seleccion;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "getSeleccionesByGrupoResponse", namespace = "http://soa.antoni.com/selecciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetSeleccionesByGrupoResponse {
    @XmlElementWrapper(name = "selecciones")
    @XmlElement(name = "seleccion")
    private List<Seleccion> selecciones = new ArrayList<>();

    public GetSeleccionesByGrupoResponse() {}
    public GetSeleccionesByGrupoResponse(List<Seleccion> selecciones) { this.selecciones = selecciones; }
    public List<Seleccion> getSelecciones() { return selecciones; }
    public void setSelecciones(List<Seleccion> selecciones) { this.selecciones = selecciones; }
}
