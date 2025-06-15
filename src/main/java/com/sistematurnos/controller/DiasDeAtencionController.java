package com.sistematurnos.controller;

import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.service.IDiasDeAtencionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-atencion")
@Tag(name = "Días de Atención", description = "Gestión de días disponibles de atención")
public class DiasDeAtencionController {

    @Autowired
    private IDiasDeAtencionService diasDeAtencionService;

    @Operation(summary = "Listar todos los días de atención")
    @GetMapping
    public List<DiasDeAtencion> listarDias() {
        return diasDeAtencionService.traerTodos();
    }

    @Operation(summary = "Obtener un día de atención por ID")
    @GetMapping("/{id}")
    public ResponseEntity<DiasDeAtencion> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(diasDeAtencionService.traer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo día de atención")
    @PostMapping
    public ResponseEntity<DiasDeAtencion> crear(@RequestBody DiasDeAtencion dia) {
        try {
            DiasDeAtencion nuevo = diasDeAtencionService.altaDiaDeAtencion(dia);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar un día de atención existente")
    @PutMapping("/{id}")
    public ResponseEntity<DiasDeAtencion> actualizar(@PathVariable int id, @RequestBody DiasDeAtencion dia) {
        try {
            dia.setId(id);
            DiasDeAtencion actualizado = diasDeAtencionService.modificarDiaDeAtencion(dia);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un día de atención (baja lógica)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            diasDeAtencionService.bajaDiaDeAtencion(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}