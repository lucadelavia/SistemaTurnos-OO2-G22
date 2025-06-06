package com.sistematurnos.services;

import java.util.List;
import java.util.Optional;

import com.sistematurnos.entities.Servicio;

public interface IServicioService{
    public List<Servicio> findAll();

    public Optional<Servicio> findById(int id);

    public Optional<Servicio> findByNombreServicio(String nombreServicio);

    public Servicio save(Servicio servicio);

    public void deleteById(int id);
}