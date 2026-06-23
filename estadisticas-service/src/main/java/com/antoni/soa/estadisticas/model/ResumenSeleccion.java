package com.antoni.soa.estadisticas.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ResumenSeleccion {
    private String seleccionId;
    private Integer partidosJugados;
    private Integer golesFavor;
    private Integer golesContra;
    private Integer diferenciaGoles;
    private Integer puntos;
    private Double posesionPromedio;

    public ResumenSeleccion() {}

    public ResumenSeleccion(String seleccionId, Integer partidosJugados, Integer golesFavor, Integer golesContra, Integer diferenciaGoles, Integer puntos, Double posesionPromedio) {
        this.seleccionId = seleccionId;
        this.partidosJugados = partidosJugados;
        this.golesFavor = golesFavor;
        this.golesContra = golesContra;
        this.diferenciaGoles = diferenciaGoles;
        this.puntos = puntos;
        this.posesionPromedio = posesionPromedio;
    }

    public String getSeleccionId() { return seleccionId; }
    public void setSeleccionId(String seleccionId) { this.seleccionId = seleccionId; }
    public Integer getPartidosJugados() { return partidosJugados; }
    public void setPartidosJugados(Integer partidosJugados) { this.partidosJugados = partidosJugados; }
    public Integer getGolesFavor() { return golesFavor; }
    public void setGolesFavor(Integer golesFavor) { this.golesFavor = golesFavor; }
    public Integer getGolesContra() { return golesContra; }
    public void setGolesContra(Integer golesContra) { this.golesContra = golesContra; }
    public Integer getDiferenciaGoles() { return diferenciaGoles; }
    public void setDiferenciaGoles(Integer diferenciaGoles) { this.diferenciaGoles = diferenciaGoles; }
    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }
    public Double getPosesionPromedio() { return posesionPromedio; }
    public void setPosesionPromedio(Double posesionPromedio) { this.posesionPromedio = posesionPromedio; }
}
