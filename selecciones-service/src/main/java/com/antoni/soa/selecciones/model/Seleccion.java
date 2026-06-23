package com.antoni.soa.selecciones.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Seleccion {
    private String id;
    private String nombre;
    private String confederacion;
    private String grupo;
    private Integer rankingFifa;
    private String entrenador;

    public Seleccion() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getConfederacion() { return confederacion; }
    public void setConfederacion(String confederacion) { this.confederacion = confederacion; }
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public Integer getRankingFifa() { return rankingFifa; }
    public void setRankingFifa(Integer rankingFifa) { this.rankingFifa = rankingFifa; }
    public String getEntrenador() { return entrenador; }
    public void setEntrenador(String entrenador) { this.entrenador = entrenador; }
}
