package com.antoni.soa.selecciones.repository;

import com.antoni.soa.selecciones.model.Seleccion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Repository
public class SeleccionesRepository {
    private final List<Seleccion> selecciones;

    public SeleccionesRepository(ObjectMapper mapper) throws IOException {
        try (InputStream inputStream = new ClassPathResource("data/selecciones.json").getInputStream()) {
            this.selecciones = List.copyOf(mapper.readValue(inputStream, new TypeReference<List<Seleccion>>() {}));
        }
    }

    public List<Seleccion> findAll() { return selecciones; }

    public Optional<Seleccion> findById(String id) {
        return selecciones.stream()
                .filter(seleccion -> seleccion.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Seleccion> findByGrupo(String grupo) {
        return selecciones.stream()
                .filter(seleccion -> seleccion.getGrupo().equalsIgnoreCase(grupo))
                .toList();
    }
}
