package com.sistematurnos.service;

import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.repository.ISucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class SucursalService {

    @Autowired
    private ISucursalRepository sucursalRepository;

    @Autowired
    private DiasDeAtencionService diasDeAtencionService;

    public Sucursal altaSucursal(String direccion, String telefono, LocalTime horaApertura, LocalTime horaCierre, int espacio) {
        if (sucursalRepository.findByTelefono(telefono).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con el teléfono: " + telefono);
        }
        Sucursal suc = new Sucursal();
        suc.setDireccion(direccion);
        suc.setTelefono(telefono);
        suc.setHoraApertura(horaApertura);
        suc.setHoraCierre(horaCierre);
        suc.setEspacio(espacio);
        return sucursalRepository.save(suc);
    }

    public Sucursal altaSucursal(Sucursal suc) {
        if (sucursalRepository.findByTelefono(suc.getTelefono()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con el teléfono: " + suc.getTelefono());
        }
        return sucursalRepository.save(suc);
    }

    public void bajaSucursal(int id) {
        Sucursal suc = traer(id);
        sucursalRepository.delete(suc);
    }

    public Sucursal modificarSucursal(Sucursal suc) {
        Sucursal actual = traer(suc.getId());
        actual.setDireccion(suc.getDireccion());
        actual.setTelefono(suc.getTelefono());
        actual.setHoraApertura(suc.getHoraApertura());
        actual.setHoraCierre(suc.getHoraCierre());
        actual.setEspacio(suc.getEspacio());
        return sucursalRepository.save(actual);
    }

    public Sucursal traer(int idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe una sucursal con ID: " + idSucursal));
    }

    public List<Sucursal> traerSucursales() {
        return sucursalRepository.findAll();
    }

    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (!suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().add(dia);
            sucursalRepository.save(suc);
        }
    }

    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().remove(dia);
            sucursalRepository.save(suc);
        }
    }

    public void asociarEspecialidad(int idSucursal, Especialidad especialidad) {
        Sucursal suc = traer(idSucursal);
        if (!suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().add(especialidad);
            sucursalRepository.save(suc);
        }
    }

    public void removerEspecialidad(int idSucursal, Especialidad especialidad) {
        Sucursal suc = traer(idSucursal);
        if (suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().remove(especialidad);
            sucursalRepository.save(suc);
        }
    }

    public Sucursal guardar(Sucursal suc) {
        return sucursalRepository.save(suc);
    }
}
