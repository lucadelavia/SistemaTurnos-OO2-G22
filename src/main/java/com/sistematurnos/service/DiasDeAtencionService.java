package com.sistematurnos.service;

import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.repository.IDiasDeAtencionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiasDeAtencionService {

    @Autowired
    private IDiasDeAtencionRepository repository;

    public DiasDeAtencion altaDiaDeAtencion(String nombre) {
        return repository.save(new DiasDeAtencion(nombre));
    }

    public DiasDeAtencion altaDiaDeAtencion(DiasDeAtencion dia) {
        return repository.save(dia);
    }

    public void bajaDiaDeAtencion(int id) {
        DiasDeAtencion dia = traer(id);
        repository.delete(dia);
    }

    public DiasDeAtencion modificarDiaDeAtencion(DiasDeAtencion dia) {
        DiasDeAtencion actual = traer(dia.getId());
        actual.setNombre(dia.getNombre());
        return repository.save(actual);
    }

    public DiasDeAtencion traer(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe un día de atención con ID: " + id));
    }

    public List<DiasDeAtencion> traerTodos() {
        return repository.findAll();
    }

    @PostConstruct
    public void initDiasPorDefecto() {
        List<String> dias = List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
        for (String dia : dias) {
            if (!repository.existsByNombre(dia)) {
                repository.save(new DiasDeAtencion(dia));
            }
        }
    }
}