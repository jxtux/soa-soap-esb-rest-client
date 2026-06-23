package com.antoni.soa.estadisticas.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Estadistica {
    private String id;
    private String partidoId;
    private String seleccionId;
    private Integer golesFavor;
    private Integer golesContra;
    private Integer posesion;
    private Integer remates;
    private Integer faltas;
    private Integer puntos;

    public Estadistica() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getPartidoId() { return partidoId; }
    public void setPartidoId(String partidoId) { this.partidoId = partidoId; }
    public String getSeleccionId() { return seleccionId; }
    public void setSeleccionId(String seleccionId) { this.seleccionId = seleccionId; }
    public Integer getGolesFavor() { return golesFavor; }
    public void setGolesFavor(Integer golesFavor) { this.golesFavor = golesFavor; }
    public Integer getGolesContra() { return golesContra; }
    public void setGolesContra(Integer golesContra) { this.golesContra = golesContra; }
    public Integer getPosesion() { return posesion; }
    public void setPosesion(Integer posesion) { this.posesion = posesion; }
    public Integer getRemates() { return remates; }
    public void setRemates(Integer remates) { this.remates = remates; }
    public Integer getFaltas() { return faltas; }
    public void setFaltas(Integer faltas) { this.faltas = faltas; }
    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }
}
