package com.antoni.soa.partidos.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Partido {
    private String id;
    private String fecha;
    private String estadio;
    private String ciudad;
    private String grupo;
    private String seleccionLocalId;
    private String seleccionVisitanteId;
    private Integer golesLocal;
    private Integer golesVisitante;
    private String estado;

    public Partido() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public String getSeleccionLocalId() { return seleccionLocalId; }
    public void setSeleccionLocalId(String seleccionLocalId) { this.seleccionLocalId = seleccionLocalId; }
    public String getSeleccionVisitanteId() { return seleccionVisitanteId; }
    public void setSeleccionVisitanteId(String seleccionVisitanteId) { this.seleccionVisitanteId = seleccionVisitanteId; }
    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }
    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
