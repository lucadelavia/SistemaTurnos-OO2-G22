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
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Empleado no encontrado con ID: " + id);
        }
    }

    @GetMapping("/cuil/{cuil}")
    public ResponseEntity<?> obtenerPorCuil(@PathVariable long cuil) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorCuil(cuil));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Empleado no encontrado con CUIL: " + cuil);
        }
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<?> obtenerPorMatricula(@PathVariable String matricula) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorMatricula(matricula));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Empleado no encontrado con matrícula: " + matricula);
        }
    }

    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            Empleado nuevo = empleadoService.altaEmpleado(empleado);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al crear empleado: " + e.getMessage());
        }
    }

    @PostMapping("/con-especialidades")
    public ResponseEntity<?> crearEmpleadoConEspecialidades(@RequestBody Empleado empleado) {
        try {
            Empleado nuevo = empleadoService.altaEmpleadoConEspecialidades(empleado);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al crear empleado con especialidades: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificarEmpleado(@PathVariable int id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            Empleado actualizado = empleadoService.modificarEmpleado(empleado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al modificar empleado: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable int id) {
        try {
            empleadoService.bajaEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al dar de baja empleado: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/especialidades")
    public ResponseEntity<?> asignarEspecialidad(@PathVariable int id, @RequestBody Especialidad especialidad) {
        try {
            empleadoService.asignarEspecialidad(id, especialidad);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al asignar especialidad: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/especialidades")
    public ResponseEntity<?> removerEspecialidad(@PathVariable int id, @RequestBody Especialidad especialidad) {
        try {
            empleadoService.removerEspecialidad(id, especialidad);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error al remover especialidad: " + e.getMessage());
        }
    }

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<Empleado>> obtenerPorEspecialidad(@PathVariable int id) {
        return ResponseEntity.ok(empleadoService.buscarPorEspecialidad(id));
    }

}
