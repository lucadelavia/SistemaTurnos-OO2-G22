package com.sistematurnos.controller;

import com.sistematurnos.entity.Establecimiento;
import com.sistematurnos.service.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping
    public List<Establecimiento> listarEstablecimientos() {
        return establecimientoService.traerEstablecimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(establecimientoService.traer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Establecimiento> crear(@RequestBody Establecimiento est) {
        try {
            return ResponseEntity.ok(establecimientoService.altaEstablecimiento(est));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> modificar(@PathVariable int id, @RequestBody Establecimiento est) {
        try {
            est.setId(id);
            return ResponseEntity.ok(establecimientoService.modificarEstablecimiento(est));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            establecimientoService.bajaEstablecimiento(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idEst}/sucursales/{idSuc}")
    public ResponseEntity<Void> asociarSucursal(@PathVariable int idEst, @PathVariable int idSuc) {
        try {
            establecimientoService.asociarSucursalAEstablecimiento(idEst, idSuc);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idEst}/sucursales/{idSuc}")
    public ResponseEntity<Void> removerSucursal(@PathVariable int idEst, @PathVariable int idSuc) {
        try {
            establecimientoService.removerSucursalDeEstablecimiento(idEst, idSuc);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
