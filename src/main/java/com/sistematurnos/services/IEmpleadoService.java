package com.sistematurnos.services;

import java.util.List;
import java.util.Optional;

import com.sistematurnos.entities.Empleado;

public interface IEmpleadoService{
    public List<Empleado> findAll();

    public Optional<Empleado> findById(int id);

    public Optional<Empleado> findByDni(int dni);

    public Optional<Empleado> findByCuil(int cuil);

    public Optional<Empleado> findByMatricula(int matricula);

    public List<Empleado> findByEspecialidad(String especialidad);

    public Empleado save(Empleado empleado);

    public void deleteById(int id);
}