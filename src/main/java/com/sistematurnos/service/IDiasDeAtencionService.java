package com.sistematurnos.service;

import com.sistematurnos.entity.DiasDeAtencion;

import java.util.List;

public interface IDiasDeAtencionService {
    public List<DiasDeAtencion> traerTodos();

    public DiasDeAtencion traer(int id);

    public DiasDeAtencion modificarDiaDeAtencion(DiasDeAtencion dia);

    public void bajaDiaDeAtencion(int id);

    public DiasDeAtencion altaDiaDeAtencion(DiasDeAtencion dia);

    public DiasDeAtencion altaDiaDeAtencion(String nombre);
}