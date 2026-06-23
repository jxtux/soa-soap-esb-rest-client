package com.antoni.soa.selecciones.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getSeleccionByIdRequest", namespace = "http://soa.antoni.com/selecciones")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetSeleccionByIdRequest {
    private String id;
    public GetSeleccionByIdRequest() {}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
