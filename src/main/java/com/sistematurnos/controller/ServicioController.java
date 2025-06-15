package com.sistematurnos.controller;

import com.sistematurnos.entity.Servicio;
<<<<<<< HEAD
import com.sistematurnos.service.IServicioService;
=======
import com.sistematurnos.service.ServicioService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    @Autowired
<<<<<<< HEAD
    private IServicioService servicioService;
=======
    private ServicioService servicioService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)

    @GetMapping
    public List<Servicio> listarServicios() {
        return servicioService.traerServicios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(servicioService.obtenerServicioPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Servicio> obtenerPorNombre(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(servicioService.obtenerServicioPorNombre(nombre));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Servicio> crear(@RequestBody Servicio servicio) {
        try {
            return ResponseEntity.ok(servicioService.altaServicio(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> modificar(@PathVariable int id, @RequestBody Servicio servicio) {
        try {
            servicio.setId(id);
            return ResponseEntity.ok(servicioService.modificarServicio(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

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
