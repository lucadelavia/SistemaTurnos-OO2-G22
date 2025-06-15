package com.sistematurnos.controller;

import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.service.IEspecialidadService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "Gestión de especialidades")
public class EspecialidadController {

    @Autowired
    private IEspecialidadService especialidadService;

    @Operation(summary = "Listar todas las especialidades")
    @GetMapping
    public List<Especialidad> listarEspecialidades() {
        return especialidadService.traerEspecialidades();
    }

    @Operation(summary = "Obtener especialidad por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener especialidad por nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Especialidad> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorNombre(nombre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva especialidad")
    @PostMapping
    public ResponseEntity<Especialidad> crear(@RequestBody Especialidad especialidad) {
        try {
            return ResponseEntity.ok(especialidadService.altaEspecialidad(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar una especialidad")
    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> modificar(@PathVariable int id, @RequestBody Especialidad especialidad) {
        try {
            especialidad.setId(id);
            return ResponseEntity.ok(especialidadService.modificarEspecialidad(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) una especialidad")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            especialidadService.bajaEspecialidad(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
