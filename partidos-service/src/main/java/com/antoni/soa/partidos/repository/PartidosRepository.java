package com.antoni.soa.partidos.repository;

import com.antoni.soa.partidos.model.Partido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class PartidosRepository {
    private final List<Partido> partidos;

    public PartidosRepository(ObjectMapper mapper) throws IOException {
        try (InputStream inputStream = new ClassPathResource("data/partidos.json").getInputStream()) {
            this.partidos = List.copyOf(mapper.readValue(inputStream, new TypeReference<List<Partido>>() {}));
        }
    }

    public List<Partido> findAll() {
        return partidos;
    }

    public Optional<Partido> findById(String id) {
        return partidos.stream()
                .filter(partido -> partido.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Partido> findBySeleccionId(String seleccionId) {
        return partidos.stream()
                .filter(partido -> partido.getSeleccionLocalId().equalsIgnoreCase(seleccionId)
                        || partido.getSeleccionVisitanteId().equalsIgnoreCase(seleccionId))
                .toList();
    }
}
