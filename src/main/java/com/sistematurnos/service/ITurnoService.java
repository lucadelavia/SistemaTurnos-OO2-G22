package com.sistematurnos.service;

import com.sistematurnos.entity.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public interface ITurnoService {
    public Turno altaTurno(LocalDateTime fechaHora, boolean estadoActivo, String codigo,
                           Servicio servicio, Cliente cliente, Empleado empleado, Sucursal sucursal);
    
    public Turno altaTurno(Turno turno);

    public Turno obtenerTurnoPorId(int id);

    public void bajaTurno(int id);

    public Turno modificarTurno(Turno turno);

    public List<LocalDateTime> obtenerHorariosDisponibles(int idSucursal, int idServicio, int idEmpleado, LocalDate fecha);

    public List<Turno> obtenerTurnosPorSucursal(int idSucursal);

    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha, boolean estado);

    public List<Turno> obtenerTurnosPorRangoFechas(LocalDate desde, LocalDate hasta);

    public List<Turno> obtenerTurnosPorCliente(int idCliente);

    public List<Turno> obtenerTurnosPorEmpleado(int idEmpleado);

    public List<Turno> obtenerTurnosPorServicio(int idServicio);

    public Turno obtenerTurnoPorCodigo(String codigo);

    public List<Turno> traerTurnosPorFechaYSucursal(LocalDate fecha, int idSucursal);

    public List<Turno> traerTurnosPorFechaYServicio(LocalDate fecha, int idServicio);

    public List<Turno> traerTurnosPorFechaYCliente(LocalDate fecha, int idCliente);

    public List<Turno> traerTurnosPorFechaYEmpleado(LocalDate fecha, int idEmpleado);

    public List<Turno> traerTurnos();
}
