package com.sistematurnos.controller;

import com.sistematurnos.entity.Turno;
<<<<<<< HEAD
import com.sistematurnos.service.ITurnoService;
=======
import com.sistematurnos.service.TurnoService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired
<<<<<<< HEAD
    private ITurnoService turnoService;
=======
    private TurnoService turnoService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)

    @GetMapping
    public List<Turno> listarTurnos() {
        return turnoService.traerTurnos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Turno> obtenerPorId(@PathVariable int id) {
        try {
            return ResponseEntity.ok(turnoService.obtenerTurnoPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Turno> crear(@RequestBody Turno turno) {
        try {
            return ResponseEntity.ok(turnoService.altaTurno(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Turno> modificar(@PathVariable int id, @RequestBody Turno turno) {
        try {
            turno.setId(id);
            return ResponseEntity.ok(turnoService.modificarTurno(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            turnoService.bajaTurno(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<List<LocalDateTime>> obtenerHorariosDisponibles(
            @RequestParam int idSucursal,
            @RequestParam int idServicio,
            @RequestParam int idEmpleado,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        try {
            List<LocalDateTime> disponibles = turnoService.obtenerHorariosDisponibles(idSucursal, idServicio, idEmpleado, fecha);
            return ResponseEntity.ok(disponibles);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/cliente/{id}")
    public List<Turno> turnosPorCliente(@PathVariable int id) {
        return turnoService.obtenerTurnosPorCliente(id);
    }

    @GetMapping("/empleado/{id}")
    public List<Turno> turnosPorEmpleado(@PathVariable int id) {
        return turnoService.obtenerTurnosPorEmpleado(id);
    }

    @GetMapping("/sucursal/{id}")
    public List<Turno> turnosPorSucursal(@PathVariable int id) {
        return turnoService.obtenerTurnosPorSucursal(id);
    }

    @GetMapping("/servicio/{id}")
    public List<Turno> turnosPorServicio(@PathVariable int id) {
        return turnoService.obtenerTurnosPorServicio(id);
    }

    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<Turno> turnoPorCodigo(@PathVariable String codigo) {
        try {
            return ResponseEntity.ok(turnoService.obtenerTurnoPorCodigo(codigo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha")
    public List<Turno> turnosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam boolean estado) {
        return turnoService.obtenerTurnosPorFecha(fecha, estado);
    }

    @GetMapping("/rango-fechas")
    public List<Turno> turnosEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return turnoService.obtenerTurnosPorRangoFechas(desde, hasta);
    }

    @GetMapping("/por-fecha-sucursal")
    public List<Turno> turnosPorFechaYSucursal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idSucursal) {
        return turnoService.traerTurnosPorFechaYSucursal(fecha, idSucursal);
    }

    @GetMapping("/por-fecha-servicio")
    public List<Turno> turnosPorFechaYServicio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idServicio) {
        return turnoService.traerTurnosPorFechaYServicio(fecha, idServicio);
    }

    @GetMapping("/por-fecha-cliente")
    public List<Turno> turnosPorFechaYCliente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idCliente) {
        return turnoService.traerTurnosPorFechaYCliente(fecha, idCliente);
    }

    @GetMapping("/por-fecha-empleado")
    public List<Turno> turnosPorFechaYEmpleado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idEmpleado) {
        return turnoService.traerTurnosPorFechaYEmpleado(fecha, idEmpleado);
    }
}
