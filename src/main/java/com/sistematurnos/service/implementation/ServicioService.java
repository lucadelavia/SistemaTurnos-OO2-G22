package com.sistematurnos.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistematurnos.entity.Servicio;
import com.sistematurnos.repository.IServicioRepository;
import com.sistematurnos.service.IServicioService;

@Service
public class ServicioService implements IServicioService{
    
    @Autowired
    private IServicioRepository servicioRepository;

    @Override
    public Servicio altaServicio(String nombreServicio, int duracion) {
        if (servicioRepository.findByNombreServicio(nombreServicio).isPresent()) {
            throw new IllegalArgumentException("Este Servicio ya existe.");
        }
        Servicio s = new Servicio(nombreServicio, duracion);
        return servicioRepository.save(s);
    }

    @Override
    public Servicio altaServicio(Servicio servicio) {
        if (servicioRepository.findByNombreServicio(servicio.getNombreServicio()).isPresent()) {
            throw new IllegalArgumentException("Este Servicio ya existe.");
        }
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio obtenerServicioPorNombre(String nombreServicio) {
        return servicioRepository.findByNombreServicio(nombreServicio)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe servicio con Nombre: " + nombreServicio));
    }

    @Override
    public Servicio obtenerServicioPorId(int id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe servicio con ID: " + id));
    }

    @Override
    public void bajaServicio(int id) {
        Servicio servicio = obtenerServicioPorId(id);
        servicio.setEstado(false);
        servicioRepository.save(servicio);
    }

    @Override
    public Servicio modificarServicio(Servicio servicio) {
        Servicio actual = obtenerServicioPorId(servicio.getId());
        actual.setNombreServicio(servicio.getNombreServicio());
        actual.setDuracion(servicio.getDuracion());
        return servicioRepository.save(actual);
    }

    @Override
    public List<Servicio> traerServicios() {
        return servicioRepository.findByEstadoTrue();
    }

}
