package com.antoni.soa.estadisticas.repository;

import com.antoni.soa.estadisticas.model.Estadistica;
import com.antoni.soa.estadisticas.model.ResumenSeleccion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Repository
public class EstadisticasRepository {
    private final List<Estadistica> estadisticas;

    public EstadisticasRepository(ObjectMapper mapper) throws IOException {
        try (InputStream inputStream = new ClassPathResource("data/estadisticas.json").getInputStream()) {
            this.estadisticas = List.copyOf(mapper.readValue(inputStream, new TypeReference<List<Estadistica>>() {}));
        }
    }

    public List<Estadistica> findAll() { return estadisticas; }

    public List<Estadistica> findBySeleccionId(String seleccionId) {
        return estadisticas.stream()
                .filter(estadistica -> estadistica.getSeleccionId().equalsIgnoreCase(seleccionId))
                .toList();
    }

    public List<Estadistica> findByPartidoId(String partidoId) {
        return estadisticas.stream()
                .filter(estadistica -> estadistica.getPartidoId().equalsIgnoreCase(partidoId))
                .toList();
    }

    public ResumenSeleccion resumenBySeleccionId(String seleccionId) {
        List<Estadistica> datos = findBySeleccionId(seleccionId);
        int partidos = datos.size();
        int golesFavor = datos.stream().mapToInt(Estadistica::getGolesFavor).sum();
        int golesContra = datos.stream().mapToInt(Estadistica::getGolesContra).sum();
        int puntos = datos.stream().mapToInt(Estadistica::getPuntos).sum();
        double posesionPromedio = datos.stream().mapToInt(Estadistica::getPosesion).average().orElse(0.0);
        return new ResumenSeleccion(seleccionId, partidos, golesFavor, golesContra, golesFavor - golesContra, puntos, posesionPromedio);
    }
}
