package com.sistematurnos.service.implementation;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.repository.IEspecialidadRepository;
import com.sistematurnos.repository.ISucursalRepository;
import com.sistematurnos.service.ISucursalService;
import com.sistematurnos.service.IDiasDeAtencionService;

@Service
public class SucursalService implements ISucursalService{
    @Autowired
    private ISucursalRepository sucursalRepository;
    @Autowired
    private IDiasDeAtencionService diasDeAtencionService;
    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Override
    public Sucursal altaSucursal(String direccion, String telefono, LocalTime horaApertura, LocalTime horaCierre, int espacio) {
        if (sucursalRepository.findAll().stream().anyMatch(s -> s.getDireccion().equalsIgnoreCase(direccion))) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con la dirección: " + direccion);
        }

        Sucursal suc = new Sucursal();
        suc.setDireccion(direccion);
        suc.setTelefono(telefono);
        suc.setHoraApertura(horaApertura);
        suc.setHoraCierre(horaCierre);
        suc.setEspacio(espacio);
        suc.setEstado(true);
        return sucursalRepository.save(suc);
    }

    @Override
    public Sucursal altaSucursal(Sucursal suc) {
        // Validar dirección duplicada

        if (sucursalRepository.findAll().stream().anyMatch(s -> s.getDireccion().equalsIgnoreCase(suc.getDireccion()))) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con la dirección: " + suc.getDireccion());
        }

        suc.setEstado(true);

        if (suc.getLstDiasDeAtencion() != null && !suc.getLstDiasDeAtencion().isEmpty()) {
            Set<DiasDeAtencion> dias = suc.getLstDiasDeAtencion().stream()
                    .map(d -> diasDeAtencionService.traer(d.getId()))
                    .collect(Collectors.toSet());
            suc.setLstDiasDeAtencion(dias);
        }

        if (suc.getLstEspecialidad() != null && !suc.getLstEspecialidad().isEmpty()) {
            Set<Especialidad> especialidades = suc.getLstEspecialidad().stream()
                    .map(e -> especialidadRepository.findById(e.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + e.getId())))
                    .collect(Collectors.toSet());
            suc.setLstEspecialidad(especialidades);
        }

        return sucursalRepository.save(suc);
    }

    @Override
    public void bajaSucursal(int id) {
        Sucursal suc = traer(id);
        suc.setEstado(false);
        sucursalRepository.save(suc);
    }

    @Override
    public Sucursal modificarSucursal(Sucursal suc) {
        Sucursal actual = traer(suc.getId());
        actual.setDireccion(suc.getDireccion());
        actual.setTelefono(suc.getTelefono());
        actual.setHoraApertura(suc.getHoraApertura());
        actual.setHoraCierre(suc.getHoraCierre());
        actual.setEspacio(suc.getEspacio());

        if (suc.getLstDiasDeAtencion() != null) {
            Set<DiasDeAtencion> dias = suc.getLstDiasDeAtencion().stream()
                    .map(d -> diasDeAtencionService.traer(d.getId()))
                    .collect(Collectors.toSet());
            actual.setLstDiasDeAtencion(dias);
        }

        if (suc.getLstEspecialidad() != null) {
            Set<Especialidad> especialidades = suc.getLstEspecialidad().stream()
                    .map(e -> especialidadRepository.findById(e.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + e.getId())))
                    .collect(Collectors.toSet());
            actual.setLstEspecialidad(especialidades);
        }

        return sucursalRepository.save(actual);
    }

    @Override
    public Sucursal traer(int idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe una sucursal con ID: " + idSucursal));
    }

    @Override
    public List<Sucursal> traerSucursales() {
        return sucursalRepository.findAll().stream()
                .filter(Sucursal::isEstado)
                .toList();
    }

    @Override
    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (!suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().add(dia);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().remove(dia);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void asociarEspecialidad(int idSucursal, Especialidad especialidad) {
        Sucursal suc = traer(idSucursal);
        if (!suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().add(especialidad);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void removerEspecialidad(int idSucursal, Especialidad especialidad) {

        Sucursal suc = traer(idSucursal);
        if (suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().remove(especialidad);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public Sucursal guardar(Sucursal suc) {
        return sucursalRepository.save(suc);
    }
}
