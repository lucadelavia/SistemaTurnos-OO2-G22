package com.sistematurnos.controller;

import com.sistematurnos.entity.Especialidad;
<<<<<<< HEAD
import com.sistematurnos.service.IEspecialidadService;
=======
import com.sistematurnos.service.EspecialidadService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
<<<<<<< HEAD
    private IEspecialidadService especialidadService;
=======
    private EspecialidadService especialidadService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)

    @GetMapping
    public List<Especialidad> listarEspecialidades() {
        return especialidadService.traerEspecialidades();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Especialidad> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(especialidadService.obtenerEspecialidadPorNombre(nombre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Especialidad> crear(@RequestBody Especialidad especialidad) {
        try {
            return ResponseEntity.ok(especialidadService.altaEspecialidad(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> modificar(@PathVariable int id, @RequestBody Especialidad especialidad) {
        try {
            especialidad.setId(id);
            return ResponseEntity.ok(especialidadService.modificarEspecialidad(especialidad));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

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
