package com.sistematurnos.service;

import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.repository.ISucursalRepository;
import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.entity.Especialidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class SucursalService {

    @Autowired
    private ISucursalRepository sucursalRepository;

    @Autowired
    private DiasDeAtencionService diasDeAtencionService;

    public Sucursal altaSucursal(String direccion, String telefono, LocalTime horaApertura, LocalTime horaCierre, int espacio) {
        Sucursal suc = new Sucursal(direccion, telefono, horaApertura, horaCierre, espacio);
    	
    	if (sucursalRepository.findByTelefono(telefono).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con el telefono: " + telefono);
        }
        
        return sucursalRepository.save(suc);
    }

    public Sucursal altaSucursal(Sucursal suc) {
        if (sucursalRepository.findByTelefono(suc.getTelefono()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con el telefono: " + suc.getTelefono());
        }
        return sucursalRepository.save(suc);
    }

    public void bajaSucursal(int id) {
        Sucursal suc = sucursalRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe una sucursal con ID: " + id));
        sucursalRepository.delete(suc);
    }

    public Sucursal modificarSucursal(Sucursal suc) {
        Sucursal actual = sucursalRepository.findById(suc.getId())
        		.orElseThrow(() -> new IllegalArgumentException("ERROR: no existe sucursal con ID: " + suc.getId()));
        
        actual.setDireccion(suc.getDireccion());
        actual.setTelefono(suc.getTelefono());
        actual.setHoraApertura(suc.getHoraApertura());
        actual.setHoraCierre(suc.getHoraCierre());
        return sucursalRepository.save(actual);
    }

    public Sucursal traer(int idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe una sucursal con ID: " + idSucursal));
    }

    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);

        if (!suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().add(dia);
            sucursalRepository.save(suc);
        } else {
            System.out.println("La sucursal ya tiene asignado ese dia de atencion");
        }
    }

    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion) {
    	Sucursal suc = traer(idSucursal);
        
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);

        if (suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().remove(dia);
            sucursalRepository.save(suc);
        } else {
            System.out.println("La sucursal no tiene asignado ese dia de atencion");
        }
    }

    public void asociarEspecialidad(int idSucursal, Especialidad especialidad) {
    	Sucursal suc = traer(idSucursal);
    	
        if (!suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().add(especialidad);
            sucursalRepository.save(suc);
        } else {
            System.out.println("La sucursal ya tiene asignada esa especialidad");
        }
    }

    public void removerEspecialidad(int idSucursal, Especialidad especialidad) {
    	Sucursal suc = traer(idSucursal);
    	
        if (suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().remove(especialidad);
            sucursalRepository.save(suc);
        } else {
            System.out.println("La sucursal no tiene esa especialidad");
        }
    }
    
    public Sucursal guardar(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }
}