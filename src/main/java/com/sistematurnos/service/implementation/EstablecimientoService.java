package com.sistematurnos.service.implementation;

import com.sistematurnos.entity.Establecimiento;
import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.repository.IEstablecimientoRepository;
import com.sistematurnos.service.IEstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstablecimientoService implements IEstablecimientoService{
	
	@Autowired
    private IEstablecimientoRepository establecimientoRepository;

    @Autowired
    private SucursalService sucursalService;

    @Override
    public Establecimiento altaEstablecimiento(String nombre, String cuit, String direccion, String descripcion) {
        Establecimiento est = new Establecimiento(nombre, cuit, direccion, descripcion);

        if (establecimientoRepository.findByNombre(nombre).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el nombre: " + nombre);
        }
        if (establecimientoRepository.findByCuit(cuit).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el CUIT: " + cuit);
        }

        return establecimientoRepository.save(est);
    }
    
    @Override
    public Establecimiento altaEstablecimiento(Establecimiento est) {
        if (establecimientoRepository.findByNombre(est.getNombre()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el nombre: " + est.getNombre());
        }
        if (establecimientoRepository.findByCuit(est.getCuit()).isPresent()) {
            throw new IllegalArgumentException("ERROR: ya existe un establecimiento con el CUIT: " + est.getCuit());
        }

        return establecimientoRepository.save(est);
    }
    
    @Override
    public void bajaEstablecimiento(int id) {
        Establecimiento est = establecimientoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + id));
        establecimientoRepository.delete(est);
    }

    @Override
    public Establecimiento modificarEstablecimiento(Establecimiento est) {
        Establecimiento actual = establecimientoRepository.findById(est.getId())
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + est.getId()));

        actual.setNombre(est.getNombre());
        actual.setCuit(est.getCuit());
        actual.setDireccion(est.getDireccion());
        actual.setDescripcion(est.getDescripcion());

        return establecimientoRepository.save(actual);
    }

    @Override
    public Establecimiento traer(int idEstablecimiento) {
        return establecimientoRepository.findById(idEstablecimiento)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe establecimiento con ID: " + idEstablecimiento));
    }

    @Override
    public List<Establecimiento> traerEstablecimientos() {
        return establecimientoRepository.findAll();
    }

    @Override
    public void asociarSucursalAEstablecimiento(int idEst, int idSuc) {
        Establecimiento est = traer(idEst);
        Sucursal suc = sucursalService.traer(idSuc);
        if (!est.getSucursales().contains(suc)) {
            est.getSucursales().add(suc);
        }
        suc.setEstablecimiento(est);
        establecimientoRepository.save(est);
        sucursalService.guardar(suc);
    }

    @Override
    public void removerSucursalDeEstablecimiento(int idEst, int idSuc) {
        Establecimiento est = traer(idEst);
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