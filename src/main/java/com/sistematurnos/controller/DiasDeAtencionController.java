package com.sistematurnos.controller;

import com.sistematurnos.entity.DiasDeAtencion;
import com.sistematurnos.service.DiasDeAtencionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dias-atencion")
public class DiasDeAtencionController {

    @Autowired
    private DiasDeAtencionService diasDeAtencionService;

    @GetMapping
    public List<DiasDeAtencion> listarDias() {
        return diasDeAtencionService.traerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiasDeAtencion> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(diasDeAtencionService.traer(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DiasDeAtencion> crear(@RequestBody DiasDeAtencion dia) {
        try {
            DiasDeAtencion nuevo = diasDeAtencionService.altaDiaDeAtencion(dia);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

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
