package com.antoni.soa.partidos.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getPartidoByIdRequest", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetPartidoByIdRequest {
    private String id;
    public GetPartidoByIdRequest() {}
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
