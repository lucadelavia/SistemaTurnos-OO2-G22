package com.sistematurnos.controller;

import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.service.IEmpleadoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados", description = "Operaciones sobre los empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @Operation(summary = "Listar todos los empleados")
    @GetMapping
    public List<Empleado> listarEmpleados() {
        return empleadoService.traerEmpleados();
    }

    @Operation(summary = "Listar empleados que pueden realizar un servicio (por especialidad)")
    @GetMapping("/servicio/{idServicio}")
    public ResponseEntity<List<Empleado>> empleadosPorServicio(@PathVariable int idServicio) {
        try {
            List<Empleado> empleados = empleadoService.buscarPorServicio(idServicio);
            return ResponseEntity.ok(empleados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Buscar empleado por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar empleado por CUIL")
    @GetMapping("/cuil/{cuil}")
    public ResponseEntity<Empleado> obtenerPorCuil(@PathVariable long cuil) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorCuil(cuil));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar empleado por matrícula")
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Empleado> obtenerPorMatricula(@PathVariable String matricula) {
        try {
            return ResponseEntity.ok(empleadoService.obtenerEmpleadoPorMatricula(matricula));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar empleados por especialidad")
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<Empleado>> empleadosPorEspecialidad(@PathVariable int idEspecialidad) {
        try {
            List<Empleado> empleados = empleadoService.buscarPorEspecialidad(idEspecialidad);
            return ResponseEntity.ok(empleados);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Crear un nuevo empleado")
    @PostMapping
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody Empleado empleado) {
        try {
            return ResponseEntity.ok(empleadoService.altaEmpleado(empleado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Crear empleado con especialidades")
    @PostMapping("/con-especialidades")
    public ResponseEntity<Empleado> crearEmpleadoConEspecialidades(@RequestBody Empleado empleado) {
        try {
            Empleado nuevo = empleadoService.altaEmpleadoConEspecialidades(empleado);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Modificar un empleado existente")
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> modificarEmpleado(@PathVariable int id, @RequestBody Empleado empleado) {
        try {
            empleado.setId(id);
            return ResponseEntity.ok(empleadoService.modificarEmpleado(empleado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Dar de baja un empleado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable int id) {
        try {
            empleadoService.bajaEmpleado(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Asignar una especialidad a un empleado")
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

    @Operation(summary = "Remover una especialidad de un empleado")
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
