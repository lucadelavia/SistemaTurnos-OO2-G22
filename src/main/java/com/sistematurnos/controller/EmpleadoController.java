package com.sistematurnos.controller;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.service.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.traerEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cuil/{cuil}")
    public ResponseEntity<Empleado> obtenerPorCuil(@PathVariable long cuil) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorCuil(cuil));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Empleado> obtenerPorMatricula(@PathVariable String matricula) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorMatricula(matricula));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok(empleadoService.altaEmpleado(empleado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/con-especialidades")
    public ResponseEntity<Empleado> crearEmpleadoConEspecialidades(@RequestBody Empleado empleado) {
        try {
            Empleado nuevo = empleadoService.altaEmpleadoConEspecialidades(empleado);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> modificarEmpleado(@PathVariable int id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            return ResponseEntity.ok(empleadoService.modificarEmpleado(empleado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable int id) {
        try {
            empleadoService.bajaEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/especialidades")
    public ResponseEntity<Void> asignarEspecialidad(
            @PathVariable int id,
            @RequestBody Especialidad especialidad
    ) {
        try {
            empleadoService.asignarEspecialidad(id, especialidad);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}/especialidades")
    public ResponseEntity<Void> removerEspecialidad(
            @PathVariable int id,
            @RequestBody Especialidad especialidad
    ) {
        try {
            empleadoService.removerEspecialidad(id, especialidad);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
