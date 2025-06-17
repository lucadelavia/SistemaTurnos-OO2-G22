package com.sistematurnos.controller;

import com.sistematurnos.entity.Turno;
import com.sistematurnos.service.ITurnoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Turnos", description = "Operaciones relacionadas con los turnos")
@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
    private ITurnoService turnoService;

    @Operation(summary = "Listar todos los turnos")
    @GetMapping
    public List<Turno> listarTurnos() {
        return turnoService.traerTurnos();
    }

    @Operation(summary = "Obtener un turno por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(turnoService.obtenerTurnoPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo turno")
    @PostMapping
    public ResponseEntity<Turno> crear(@RequestBody Turno turno) {
        try {
            return ResponseEntity.ok(turnoService.altaTurno(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar un turno existente")
    @PutMapping("/{id}")
    public ResponseEntity<Turno> modificar(@PathVariable int id, @RequestBody Turno turno) {
        try {
            turno.setId(id);
            return ResponseEntity.ok(turnoService.modificarTurno(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar un turno (baja lógica)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            turnoService.bajaTurno(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener horarios disponibles para un turno")
    @GetMapping("/disponibilidad")
    public ResponseEntity<List<LocalDateTime>> obtenerHorariosDisponibles(
            @RequestParam int idSucursal,
            @RequestParam int idServicio,
            @RequestParam int idEmpleado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            return ResponseEntity.ok(
                    turnoService.obtenerHorariosDisponibles(idSucursal, idServicio, idEmpleado, fecha));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Obtener turnos por cliente")
    @GetMapping("/cliente/{id}")
    public List<Turno> turnosPorCliente(@PathVariable int id) {
        return turnoService.obtenerTurnosPorCliente(id);
    }

    @Operation(summary = "Obtener turnos por empleado")
    @GetMapping("/empleado/{id}")
    public List<Turno> turnosPorEmpleado(@PathVariable int id) {
        return turnoService.obtenerTurnosPorEmpleado(id);
    }

    @Operation(summary = "Obtener turnos por sucursal")
    @GetMapping("/sucursal/{id}")
    public List<Turno> turnosPorSucursal(@PathVariable int id) {
        return turnoService.obtenerTurnosPorSucursal(id);
    }

    @Operation(summary = "Obtener turnos por servicio")
    @GetMapping("/servicio/{id}")
    public List<Turno> turnosPorServicio(@PathVariable int id) {
        return turnoService.obtenerTurnosPorServicio(id);
    }

    @Operation(summary = "Obtener turno por código")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Turno> turnoPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(turnoService.obtenerTurnoPorCodigo(codigo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener turnos por fecha y estado")
    @GetMapping("/fecha")
    public List<Turno> turnosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam boolean estado) {
        return turnoService.obtenerTurnosPorFecha(fecha, estado);
    }

    @Operation(summary = "Obtener turnos entre dos fechas")
    @GetMapping("/rango-fechas")
    public List<Turno> turnosEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return turnoService.obtenerTurnosPorRangoFechas(desde, hasta);
    }

    @Operation(summary = "Obtener turnos por fecha y sucursal")
    @GetMapping("/por-fecha-sucursal")
    public List<Turno> turnosPorFechaYSucursal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idSucursal) {
        return turnoService.traerTurnosPorFechaYSucursal(fecha, idSucursal);
    }

    @Operation(summary = "Obtener turnos por fecha y servicio")
    @GetMapping("/por-fecha-servicio")
    public List<Turno> turnosPorFechaYServicio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idServicio) {
        return turnoService.traerTurnosPorFechaYServicio(fecha, idServicio);
    }

    @Operation(summary = "Obtener turnos por fecha y cliente")
    @GetMapping("/por-fecha-cliente")
    public List<Turno> turnosPorFechaYCliente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idCliente) {
        return turnoService.traerTurnosPorFechaYCliente(fecha, idCliente);
    }

    @Operation(summary = "Obtener turnos por fecha y empleado")
    @GetMapping("/por-fecha-empleado")
    public List<Turno> turnosPorFechaYEmpleado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idEmpleado) {
        return turnoService.traerTurnosPorFechaYEmpleado(fecha, idEmpleado);
    }

    @GetMapping("/debug-cliente/{id}")
    public List<Turno> debugCliente(@PathVariable int id) {
        List<Turno> turnos = turnoService.obtenerTurnosPorCliente(id);
        System.out.println("→ Turnos cliente " + id + ": " + turnos.size());
        turnos.forEach(t -> System.out.println(t.getId() + " | " + t.getCliente().getNombre()));
        return turnos;
    }

}