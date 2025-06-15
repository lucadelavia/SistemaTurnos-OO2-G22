package com.sistematurnos.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.repository.IEspecialidadRepository;
import com.sistematurnos.service.IEspecialidadService;

@Service
public class EspecialidadService implements IEspecialidadService{

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Override
    public Especialidad altaEspecialidad(String nombre) {
        if (especialidadRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe una especialidad con ese nombre");
        }
        Especialidad especialidad = new Especialidad(nombre);
        return especialidadRepository.save(especialidad);
    }

    @Override
    public Especialidad altaEspecialidad(Especialidad e) {
        if (especialidadRepository.findByNombre(e.getNombre()).isPresent()) {
            throw new IllegalArgumentException("ERROR: Ya existe una especialidad con ese nombre");
        }
        return especialidadRepository.save(e);
    }

    @Override
    public void bajaEspecialidad(int id) {
        Especialidad actual = especialidadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ID: " + id));
        especialidadRepository.delete(actual);
    }

    @Override
    public Especialidad modificarEspecialidad(Especialidad e) {
        Especialidad actual = especialidadRepository.findById(e.getId())
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe esa especialidad"));
        actual.setNombre(e.getNombre());
        return especialidadRepository.save(actual);
    }

    @Override
    public Especialidad obtenerEspecialidadPorNombre(String nombre) {
        return especialidadRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ese nombre"));
    }

    @Override
    public Especialidad obtenerEspecialidadPorId(int id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe especialidad con ese ID"));
    }

    @Override
    public List<Especialidad> traerEspecialidades() {
        return especialidadRepository.findAll();
    }
}
