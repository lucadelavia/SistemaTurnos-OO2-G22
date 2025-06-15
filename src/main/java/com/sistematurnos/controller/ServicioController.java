package com.sistematurnos.controller;

import com.sistematurnos.entity.Servicio;
import com.sistematurnos.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "Gestión de servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Operation(summary = "Listar todos los servicios")
    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.traerServicios();
    }

    @Operation(summary = "Obtener servicio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(servicioService.obtenerServicioPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener servicio por nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Servicio> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(servicioService.obtenerServicioPorNombre(nombre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar servicios por especialidad")
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Servicio>> serviciosPorEspecialidad(@PathVariable int idEspecialidad) {
        List<Servicio> servicios = servicioService.buscarPorEspecialidad(idEspecialidad);
        return ResponseEntity.ok(servicios);
    }

    @Operation(summary = "Crear un nuevo servicio")
    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        try {
            return ResponseEntity.ok(servicioService.altaServicio(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Modificar un servicio")
    @PutMapping("/{id}")
    public ResponseEntity<Servicio> modificar(@PathVariable int id, @RequestBody Servicio servicio) {
        try {
            servicio.setId(id);
            return ResponseEntity.ok(servicioService.modificarServicio(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) un servicio")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            servicioService.bajaServicio(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
