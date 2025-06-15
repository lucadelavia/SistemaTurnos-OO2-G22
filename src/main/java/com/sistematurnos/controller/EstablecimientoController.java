package com.sistematurnos.controller;

import com.sistematurnos.entity.Establecimiento;
import com.sistematurnos.service.EstablecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establecimientos")
@Tag(name = "Establecimientos", description = "Gestión de establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @Operation(summary = "Listar todos los establecimientos")
    @GetMapping
    public List<Establecimiento> listarEstablecimientos() {
        return establecimientoService.traerEstablecimientos();
    }

    @Operation(summary = "Obtener establecimiento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(establecimientoService.traer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo establecimiento")
    @PostMapping
    public ResponseEntity<Establecimiento> crear(@RequestBody Establecimiento est) {
        try {
            return ResponseEntity.ok(establecimientoService.altaEstablecimiento(est));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar un establecimiento")
    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> modificar(@PathVariable int id, @RequestBody Establecimiento est) {
        try {
            est.setId(id);
            return ResponseEntity.ok(establecimientoService.modificarEstablecimiento(est));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) un establecimiento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            establecimientoService.bajaEstablecimiento(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
