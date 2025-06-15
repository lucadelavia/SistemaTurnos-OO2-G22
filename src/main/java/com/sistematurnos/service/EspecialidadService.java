package com.sistematurnos.service;

import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.repository.IEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    public Especialidad altaEspecialidad(String nombre) {
        if (especialidadRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe una especialidad con ese nombre");
        }
        Especialidad especialidad = new Especialidad(nombre);
        return especialidadRepository.save(especialidad);
    }

    public Especialidad altaEspecialidad(Especialidad e) {
        if (especialidadRepository.findByNombre(e.getNombre()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe una especialidad con ese nombre");
        }
        return especialidadRepository.save(e);
    }

    public Especialidad obtenerPorId(int id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la especialidad con ID: " + id));
    }

    public void bajaEspecialidad(int id) {
        Especialidad actual = especialidadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ID: " + id));
        especialidadRepository.delete(actual);
    }

    public Especialidad modificarEspecialidad(Especialidad e) {
        Especialidad actual = especialidadRepository.findById(e.getId())
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe esa especialidad"));
        actual.setNombre(e.getNombre());
        return especialidadRepository.save(actual);
    }

    public Especialidad obtenerEspecialidadPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ese nombre"));
    }

    public Especialidad obtenerEspecialidadPorId(int id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ese ID"));
    }

    public List<Especialidad> traerEspecialidades() {
        return especialidadRepository.findAll();
    }
}
