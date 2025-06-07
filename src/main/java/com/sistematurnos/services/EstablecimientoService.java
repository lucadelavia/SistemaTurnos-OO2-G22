package com.sistematurnos.services;

import com.sistematurnos.entity.Establecimiento;
import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.repositories.IEstablecimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstablecimientoService {

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;

    @Autowired
    private SucursalService sucursalService;

    public Establecimiento altaEstablecimiento(String nombre, String cuit, String direccion, String descripcion) {
        Establecimiento est = new Establecimiento(nombre, cuit, direccion, descripcion);

        if (establecimientoRepository.findByNombreEstablecimiento(nombre).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el nombre: " + nombre);
        }
        if (establecimientoRepository.findByCuitEstablecimiento(cuit).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el CUIT: " + cuit);
        }
        
        return establecimientoRepository.save(est);
    }

    public Establecimiento altaEstablecimiento(Establecimiento est) {
        if (establecimientoRepository.findByNombreEstablecimiento(est.getNombre()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el nombre: " + est.getNombre());
        }
        if (establecimientoRepository.findByCuitEstablecimiento(est.getCuit()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el CUIT: " + est.getCuit());
        }

        return establecimientoRepository.save(est);
    }

    public void bajaEstablecimiento(int id) {
        Establecimiento est = establecimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + id));
        establecimientoRepository.delete(est);
    }

    public Establecimiento modificarEstablecimiento(Establecimiento est) {
        Establecimiento actual = establecimientoRepository.findById(est.getId())
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + est.getId()));

        actual.setNombre(est.getNombre());
        actual.setCuit(est.getCuit());
        actual.setDireccion(est.getDireccion());
        actual.setDescripcion(est.getDescripcion());

        return establecimientoRepository.save(actual);
    }

    public Establecimiento traer(int idEstablecimiento) {
        return establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + idEstablecimiento));
    }

    public void asociarSucursalAEstablecimiento(int idEst, int idSuc) {
        Establecimiento est = establecimientoRepository.findById(idEst)
            .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + idEst));

        Sucursal suc = sucursalService.traer(idSuc);

        if (!est.getSucursales().contains(suc)) {
            est.getSucursales().add(suc);
        }

        suc.setEstablecimiento(est);

        establecimientoRepository.save(est);   
        sucursalService.guardar(suc);          
    }

    public void removerSucursalDeEstablecimiento(int idEst, int idSuc) {
        Establecimiento est = establecimientoRepository.findById(idEst)
            .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + idEst));

        Sucursal suc = sucursalService.traer(idSuc);

        if (!est.getSucursales().contains(suc)) {
            throw new IllegalStateException("La sucursal no pertenece a ese establecimiento");
        }

        est.getSucursales().remove(suc);

        suc.setEstablecimiento(null);

        establecimientoRepository.save(est);
        sucursalService.guardar(suc);
    }
}