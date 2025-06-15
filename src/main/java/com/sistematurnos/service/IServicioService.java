package com.sistematurnos.service;

import com.sistematurnos.entity.Servicio;

import java.util.List;

public interface IServicioService {
    public Servicio altaServicio(String nombreServicio, int duracion);
    
    public Servicio altaServicio(Servicio servicio);

    public Servicio obtenerServicioPorNombre(String nombreServicio);

    public Servicio obtenerServicioPorId(int id);

    public void bajaServicio(int id);

    public Servicio modificarServicio(Servicio servicio);

    public List<Servicio> traerServicios();
}
