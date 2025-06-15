package com.sistematurnos.controller;

import com.sistematurnos.dtos.request.TurnoRequest;
import com.sistematurnos.dtos.response.TurnoResponse;
import com.sistematurnos.dtos.mapper.TurnoMapper;
import com.sistematurnos.entity.*;
import com.sistematurnos.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Turnos", description = "Operaciones relacionadas con los turnos")
@RestController
@RequestMapping("/api/turnos")
public class TurnoController {

    @Autowired private ITurnoService turnoService;
    @Autowired private IClienteService clienteService;
    @Autowired private IEmpleadoService empleadoService;
    @Autowired private ISucursalService sucursalService;
    @Autowired private IServicioService servicioService;

    @Operation(summary = "Listar todos los turnos")
    @GetMapping
    public List<TurnoResponse> listarTurnos() {
        return turnoService.traerTurnos().stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener un turno por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponse> obtenerPorId(@PathVariable int id) {
        try {
            Turno turno = turnoService.obtenerTurnoPorId(id);
            return ResponseEntity.ok(TurnoMapper.toResponse(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo turno")
    @PostMapping
    public ResponseEntity<TurnoResponse> crear(@RequestBody TurnoRequest request) {
        try {
            Cliente cliente = clienteService.obtenerClientePorId(request.idCliente());
            Empleado empleado = empleadoService.obtenerEmpleadoPorId(request.idEmpleado());
            Sucursal sucursal = sucursalService.traer(request.idSucursal());
            Servicio servicio = servicioService.obtenerServicioPorId(request.idServicio());

            Turno nuevo = TurnoMapper.toEntity(request, cliente, empleado, sucursal, servicio);
            Turno guardado = turnoService.altaTurno(nuevo);

            return ResponseEntity.ok(TurnoMapper.toResponse(guardado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar un turno existente")
    @PutMapping("/{id}")
    public ResponseEntity<TurnoResponse> modificar(@PathVariable int id, @RequestBody TurnoRequest request) {
        try {
            Cliente cliente = clienteService.obtenerClientePorId(request.idCliente());
            Empleado empleado = empleadoService.obtenerEmpleadoPorId(request.idEmpleado());
            Sucursal sucursal = sucursalService.traer(request.idSucursal());
            Servicio servicio = servicioService.obtenerServicioPorId(request.idServicio());

            Turno turno = TurnoMapper.toEntity(request, cliente, empleado, sucursal, servicio);
            turno.setId(id);
            Turno actualizado = turnoService.modificarTurno(turno);

            return ResponseEntity.ok(TurnoMapper.toResponse(actualizado));
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
    public List<TurnoResponse> turnosPorCliente(@PathVariable int id) {
        return turnoService.obtenerTurnosPorCliente(id).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por empleado")
    @GetMapping("/empleado/{id}")
    public List<TurnoResponse> turnosPorEmpleado(@PathVariable int id) {
        return turnoService.obtenerTurnosPorEmpleado(id).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por sucursal")
    @GetMapping("/sucursal/{id}")
    public List<TurnoResponse> turnosPorSucursal(@PathVariable int id) {
        return turnoService.obtenerTurnosPorSucursal(id).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por servicio")
    @GetMapping("/servicio/{id}")
    public List<TurnoResponse> turnosPorServicio(@PathVariable int id) {
        return turnoService.obtenerTurnosPorServicio(id).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turno por código")
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<TurnoResponse> turnoPorCodigo(@PathVariable String codigo) {
        try {
            Turno turno = turnoService.obtenerTurnoPorCodigo(codigo);
            return ResponseEntity.ok(TurnoMapper.toResponse(turno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener turnos por fecha y estado")
    @GetMapping("/fecha")
    public List<TurnoResponse> turnosPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam boolean estado) {
        return turnoService.obtenerTurnosPorFecha(fecha, estado).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos entre dos fechas")
    @GetMapping("/rango-fechas")
    public List<TurnoResponse> turnosEntreFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return turnoService.obtenerTurnosPorRangoFechas(desde, hasta).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por fecha y sucursal")
    @GetMapping("/por-fecha-sucursal")
    public List<TurnoResponse> turnosPorFechaYSucursal(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idSucursal) {
        return turnoService.traerTurnosPorFechaYSucursal(fecha, idSucursal).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por fecha y servicio")
    @GetMapping("/por-fecha-servicio")
    public List<TurnoResponse> turnosPorFechaYServicio(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idServicio) {
        return turnoService.traerTurnosPorFechaYServicio(fecha, idServicio).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por fecha y cliente")
    @GetMapping("/por-fecha-cliente")
    public List<TurnoResponse> turnosPorFechaYCliente(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idCliente) {
        return turnoService.traerTurnosPorFechaYCliente(fecha, idCliente).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener turnos por fecha y empleado")
    @GetMapping("/por-fecha-empleado")
    public List<TurnoResponse> turnosPorFechaYEmpleado(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam int idEmpleado) {
        return turnoService.traerTurnosPorFechaYEmpleado(fecha, idEmpleado).stream()
                .map(TurnoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
