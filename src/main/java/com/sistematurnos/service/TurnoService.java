package com.sistematurnos.service;

import com.sistematurnos.entity.*;
import com.sistematurnos.repository.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private EmpleadoService empleadoService;


    public Turno altaTurno(LocalDateTime fechaHora, boolean estadoActivo, String codigo,
                           Servicio servicio, Cliente cliente, Empleado empleado, Sucursal sucursal) {

        if (turnoRepository.findByCodigo(codigo).isPresent()) {
            throw new IllegalArgumentException("Ya existe un turno con el código: " + codigo);
        }

        Turno t = new Turno(fechaHora, estadoActivo, codigo, servicio, cliente, sucursal, empleado);
        return turnoRepository.save(t);
    }

    public Turno altaTurno(Turno turno) {
        if (turnoRepository.findByCodigo(turno.getCodigo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un turno con el código: " + turno.getCodigo());
        }
        return turnoRepository.save(turno);
    }

    public List<LocalDateTime> obtenerHorariosDisponibles(int idSucursal, int idServicio, int idEmpleado, LocalDate fecha) {
        Sucursal sucursal = sucursalService.traer(idSucursal);
        Servicio servicio = servicioService.obtenerServicioPorId(idServicio);
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);

        if (sucursal == null || servicio == null || empleado == null) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        // Convertir el día a texto en español, ej: "lunes"
        final String diaNombre = fecha.getDayOfWeek()
                .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "ES"))
                .toLowerCase();

        boolean esDiaDeAtencion = sucursal.getLstDiasDeAtencion().stream()
                .anyMatch(d -> d.getNombre().equalsIgnoreCase(diaNombre));

        if (!esDiaDeAtencion) {
            return List.of(); // no hay turnos ese día
        }

        int duracion = servicio.getDuracion();
        int capacidad = sucursal.getEspacio();

        List<Turno> turnosDelDia = traerTurnosPorFechaYSucursal(fecha, idSucursal);

        List<LocalDateTime> disponibles = new ArrayList<>();
        LocalDateTime inicio = fecha.atTime(sucursal.getHoraApertura());
        LocalDateTime fin = fecha.atTime(sucursal.getHoraCierre());

        while (!inicio.plusMinutes(duracion).isAfter(fin)) {
            LocalDateTime finalInicio = inicio;
            int cantidadEnEstaFranja = (int) turnosDelDia.stream()
                    .filter(t -> t.getFechaHora()
                            .truncatedTo(ChronoUnit.MINUTES)
                            .isEqual(finalInicio.truncatedTo(ChronoUnit.MINUTES)))
                    .count();

            if (cantidadEnEstaFranja < capacidad) {
                disponibles.add(inicio);
            }

            inicio = inicio.plusMinutes(duracion);
        }

        return disponibles;
    }



    public Turno obtenerTurnoPorId(int id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe turno con ID: " + id));
    }

    public void bajaTurno(int id) {
        Turno t = obtenerTurnoPorId(id);
        turnoRepository.delete(t);
    }

    public Turno modificarTurno(Turno turno) {
        Turno actual = obtenerTurnoPorId(turno.getId());

        actual.setFechaHora(turno.getFechaHora());
        actual.setEstado(turno.isEstado());
        actual.setCodigo(turno.getCodigo());
        actual.setServicio(turno.getServicio());
        actual.setCliente(turno.getCliente());
        actual.setEmpleado(turno.getEmpleado());
        actual.setSucursal(turno.getSucursal());

        return turnoRepository.save(actual);
    }

    public List<Turno> obtenerTurnosPorSucursal(int idSucursal) {
        return turnoRepository.findBySucursal_Id(idSucursal);
    }

    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha, boolean estado) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndEstado(inicio, fin, estado);
    }

    public List<Turno> obtenerTurnosPorRangoFechas(LocalDate desde, LocalDate hasta) {
        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();
        return turnoRepository.findByFechaHoraBetween(inicio, fin);
    }

    public List<Turno> obtenerTurnosPorCliente(int idCliente) {
        return turnoRepository.findByCliente_Id(idCliente);
    }

    public List<Turno> obtenerTurnosPorEmpleado(int idEmpleado) {
        return turnoRepository.findByEmpleado_Id(idEmpleado);
    }

    public List<Turno> obtenerTurnosPorServicio(int idServicio) {
        return turnoRepository.findByServicio_Id(idServicio);
    }

    public Turno obtenerTurnoPorCodigo(String codigo) {
        return turnoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("No existe un turno con ese código"));
    }

    public List<Turno> traerTurnosPorFechaYSucursal(LocalDate fecha, int idSucursal) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findBySucursal_IdAndFechaHoraBetween(idSucursal, inicio, fin);
    }

    public List<Turno> traerTurnosPorFechaYServicio(LocalDate fecha, int idServicio) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByServicio_IdAndFechaHoraBetween(idServicio, inicio, fin);
    }

    public List<Turno> traerTurnosPorFechaYCliente(LocalDate fecha, int idCliente) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByCliente_IdAndFechaHoraBetween(idCliente, inicio, fin);
    }

    public List<Turno> traerTurnosPorFechaYEmpleado(LocalDate fecha, int idEmpleado) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByEmpleado_IdAndFechaHoraBetween(idEmpleado, inicio, fin);
    }

    public List<Turno> traerTurnos() {
        return turnoRepository.findAll();
    }
}
