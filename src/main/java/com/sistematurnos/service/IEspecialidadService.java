package com.sistematurnos.service;

import java.util.List;
import java.util.Optional;

import com.sistematurnos.entities.Especialidad;

public interface IEspecialidadService{
    public List<Especialidad> findAll();

    public Optional<Especialidad> findById(int id);

    public Optional<Especialidad> findByNombre(String nombre);

    public Especialidad save(Especialidad especialidad);

    public void deleteById(int id);
}