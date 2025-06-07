package com.sistematurnos.service;

import com.sistematurnos.entity.*;
import com.sistematurnos.repositories.ITurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private ITurnoRepository turnoRepository;

    public Turno altaTurno(LocalDateTime fechaHora, boolean estadoActivo, String codigo,
                           Servicio servicio, Cliente cliente, Empleado empleado, Sucursal sucursal) {

        Turno t = new Turno(fechaHora, estadoActivo, codigo, servicio, cliente, sucursal, empleado);

        if (turnoRepository.findById(t.getId()).isPresent()) {
            throw new IllegalArgumentException("Este turno ya existe.");
        }

        return turnoRepository.save(t);
    }

    public Turno altaTurno(Turno turno) {
        if (turnoRepository.findById(turno.getId()).isPresent()) {
            throw new IllegalArgumentException("Este turno ya existe.");
        }

        return turnoRepository.save(turno);
    }

    public Turno obtenerTurnoPorId(int id) {
        return turnoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("ERROR: No existe turno con ID: " + id));
    }

    public void bajaTurno(int id) {
        Turno t = obtenerTurnoPorId(id);
        turnoRepository.delete(t);
    }

    public Turno modificarServicio(Turno turno) {
        Turno actual = obtenerTurnoPorId(turno.getId());

        actual.setFechaHora(turno.getFechaHora());
        actual.setEstado(turno.isEstado());
        actual.setCodigo(turno.getCodigo());
        actual.setServicio(turno.getServicio());
        actual.setCliente(turno.getCliente());

        return turnoRepository.save(actual);
    }

    public List<Turno> obtenerTurnosPorSucursal(int idSucursal) {
        List<Turno> turnos = turnoRepository.findBySucursalId(idSucursal);
        if (turnos.isEmpty()) {
            throw new IllegalArgumentException("ERROR: Esta sucursal no tiene turnos.");
        }
        return turnos;
    }

    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha, boolean estado) {
        var inicio = fecha.atStartOfDay();
        var fin = inicio.plusDays(1);
        var turnos = turnoRepository.findByFechaHoraBetweenAndEstado(inicio, fin, estado);

        if (turnos.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No hay turnos en esta fecha: " + fecha);
        }

        return turnos;
    }

    public List<Turno> obtenerTurnosPorRangoFechas(LocalDate desde, LocalDate hasta) {
        var inicio = desde.atStartOfDay();
        var fin = hasta.plusDays(1).atStartOfDay();
        var turnos = turnoRepository.findByFechaHoraBetween(inicio, fin);

        if (turnos.isEmpty()) {
            throw new IllegalArgumentException("ERROR: No hay turnos en este rango de fechas.");
        }

        return turnos;
    }

    public List<Turno> obtenerTurnosPorCliente(int idCliente) {
        return turnoRepository.findByClienteId(idCliente);
    }

    public List<Turno> obtenerTurnosPorEmpleado(int idEmpleado) {
        return turnoRepository.findByEmpleadoId(idEmpleado);
    }

    public List<Turno> obtenerTurnosPorServicio(int idServicio) {
        return turnoRepository.findByServicioId(idServicio);
    }

    public List<Turno> traerTurnosPorFechaYSucursal(LocalDate fecha, int idSucursal) {
        var inicio = fecha.atStartOfDay();
        var fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndSucursalId(inicio, fin, idSucursal);
    }

    public List<Turno> traerTurnosPorFechaYServicio(LocalDate fecha, int idServicio) {
        var inicio = fecha.atStartOfDay();
        var fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndServicioId(inicio, fin, idServicio);
    }

    public List<Turno> traerTurnosPorFechaYCliente(LocalDate fecha, int idCliente) {
        var inicio = fecha.atStartOfDay();
        var fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndClienteId(inicio, fin, idCliente);
    }

    public List<Turno> traerTurnosPorFechaYEmpleado(LocalDate fecha, int idEmpleado) {
        var inicio = fecha.atStartOfDay();
        var fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndEmpleadoId(inicio, fin, idEmpleado);
    }

    public List<Turno> traerTurnos() {
        return turnoRepository.findAll();
    }
}
