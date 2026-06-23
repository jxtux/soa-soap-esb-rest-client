package com.antoni.soa.selecciones.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getSeleccionesByGrupoRequest", namespace = "http://soa.antoni.com/selecciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetSeleccionesByGrupoRequest {
    private String grupo;
    public GetSeleccionesByGrupoRequest() {}
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
}
