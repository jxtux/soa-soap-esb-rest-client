package com.antoni.soa.partidos.soap;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "getAllPartidosRequest", namespace = "http://soa.antoni.com/partidos")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetAllPartidosRequest {}
