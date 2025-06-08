package com.sistematurnos.controller;

import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.service.SucursalService;
import com.sistematurnos.service.DiasDeAtencionService;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private DiasDeAtencionService diasDeAtencionService;

    @GetMapping
    public List<Sucursal> listarSucursales() {
        return sucursalService.traerSucursales();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(sucursalService.traer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Sucursal> crear(@RequestBody Sucursal sucursal) {
        try {
            return ResponseEntity.ok(sucursalService.altaSucursal(sucursal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sucursal> modificar(@PathVariable int id, @RequestBody Sucursal sucursal) {
        try {
            sucursal.setId(id);
            return ResponseEntity.ok(sucursalService.modificarSucursal(sucursal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            sucursalService.bajaSucursal(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{idSucursal}/especialidades")
    public ResponseEntity<Void> asociarEspecialidad(
            @PathVariable int idSucursal,
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad esp = especialidadService.obtenerEspecialidadPorId(especialidad.getId());
            sucursalService.asociarEspecialidad(idSucursal, esp);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idSucursal}/especialidades")
    public ResponseEntity<Void> removerEspecialidad(
            @PathVariable int idSucursal,
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad esp = especialidadService.obtenerEspecialidadPorId(especialidad.getId());
            sucursalService.removerEspecialidad(idSucursal, esp);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{idSucursal}/dias-atencion/{idDia}")
    public ResponseEntity<Void> asociarDiaDeAtencion(
            @PathVariable int idSucursal,
            @PathVariable int idDia) {
        try {
            sucursalService.asociarDiaDeAtencion(idSucursal, idDia);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{idSucursal}/dias-atencion/{idDia}")
    public ResponseEntity<Void> removerDiaDeAtencion(
            @PathVariable int idSucursal,
            @PathVariable int idDia) {
        try {
            sucursalService.removerDiaDeAtencion(idSucursal, idDia);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}