package com.sistematurnos.service;

import com.sistematurnos.entity.Establecimiento;

import java.util.List;

public interface IEstablecimientoService {
    public Establecimiento altaEstablecimiento(String nombre, String cuit, String direccion, String descripcion);
    
    public Establecimiento altaEstablecimiento(Establecimiento est);

    public void bajaEstablecimiento(int id);

    public Establecimiento modificarEstablecimiento(Establecimiento est);

    public Establecimiento traer(int idEstablecimiento);

    public List<Establecimiento> traerEstablecimientos();
}
