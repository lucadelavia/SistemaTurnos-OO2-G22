package com.sistematurnos.service.implementation;

import com.sistematurnos.entity.*;
import com.sistematurnos.exception.TurnoNoEncontradoException;
import com.sistematurnos.repository.ITurnoRepository;
import com.sistematurnos.service.IClienteService;
import com.sistematurnos.service.IEmailService;
import com.sistematurnos.service.IEmpleadoService;
import com.sistematurnos.service.IServicioService;
import com.sistematurnos.service.ISucursalService;
import com.sistematurnos.service.ITurnoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TurnoService implements ITurnoService{
    @Autowired
    private ITurnoRepository turnoRepository;

    @Autowired
    private ISucursalService sucursalService;

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private IEmpleadoService empleadoService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IEmailService emailService;

    @Override
    public Turno altaTurno(LocalDateTime fechaHora, boolean estadoActivo, String codigo,
                           Servicio servicio, Cliente cliente, Empleado empleado, Sucursal sucursal) {

        Turno turno = new Turno(fechaHora, estadoActivo, codigo, servicio, cliente, sucursal, empleado);
        return altaTurno(turno);
    }

    @Override
    public Turno altaTurno(Turno turno) {
        if (turnoRepository.findByCodigo(turno.getCodigo()).isPresent()) {
            throw new TurnoNoEncontradoException("Ya existe un turno con el código: " + turno.getCodigo());
        }

        Cliente clienteCompleto = clienteService.obtenerClientePorId(turno.getCliente().getId());
        Servicio servicioCompleto = servicioService.obtenerServicioPorId(turno.getServicio().getId());
        Empleado empleadoCompleto = empleadoService.obtenerEmpleadoPorId(turno.getEmpleado().getId());
        Sucursal sucursalCompleta = sucursalService.traer(turno.getSucursal().getId());

        turno.setCliente(clienteCompleto);
        turno.setServicio(servicioCompleto);
        turno.setEmpleado(empleadoCompleto);
        turno.setSucursal(sucursalCompleta);

        Turno nuevoTurno = turnoRepository.save(turno);

        try {
            if (clienteCompleto.getEmail() != null) {
                Map<String, Object> variables = new HashMap<>();
                variables.put("turno", nuevoTurno);

                System.out.println("→ Enviando mail a: " + clienteCompleto.getEmail());

                emailService.enviarEmailConHtml(
                        clienteCompleto.getEmail(),
                        "Confirmación de Turno - UNLa",
                        "turnoConfirmado",
                        variables
                );
            }
        } catch (Exception e) {
            System.err.println("Error al enviar email de confirmación: " + e.getMessage());
        }

        return nuevoTurno;
    }

    @Override
    public Turno obtenerTurnoPorId(int id) {
        return turnoRepository.findById(id)
                .orElseThrow(() -> new TurnoNoEncontradoException("ERROR: No existe turno con ID: " + id));
    }

    @Override
    public void bajaTurno(int id) {
        Turno t = obtenerTurnoPorId(id);
        turnoRepository.delete(t);
    }

    @Override
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

    @Override
    public List<LocalDateTime> obtenerHorariosDisponibles(int idSucursal, int idServicio, int idEmpleado, LocalDate fecha) {
        Sucursal sucursal = sucursalService.traer(idSucursal);
        Servicio servicio = servicioService.obtenerServicioPorId(idServicio);
        Empleado empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);

        if (sucursal == null || servicio == null || empleado == null) {
            throw new TurnoNoEncontradoException("Datos inválidos");
        }

        final String diaNombre = fecha.getDayOfWeek()
                .getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"))
                .toLowerCase();

        boolean esDiaDeAtencion = sucursal.getLstDiasDeAtencion().stream()
                .anyMatch(d -> d.getNombre().equalsIgnoreCase(diaNombre));

        if (!esDiaDeAtencion) {
            return List.of();
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

    @Override
    public List<Turno> obtenerTurnosPorSucursal(int idSucursal) {
        return turnoRepository.findBySucursal_Id(idSucursal);
    }

    @Override
    public List<Turno> obtenerTurnosPorFecha(LocalDate fecha, boolean estado) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByFechaHoraBetweenAndEstado(inicio, fin, estado);
    }

    @Override
    public List<Turno> obtenerTurnosPorRangoFechas(LocalDate desde, LocalDate hasta) {
        LocalDateTime inicio = desde.atStartOfDay();
        LocalDateTime fin = hasta.plusDays(1).atStartOfDay();
        return turnoRepository.findByFechaHoraBetween(inicio, fin);
    }

    @Override
    public List<Turno> obtenerTurnosPorCliente(int idCliente) {
        return turnoRepository.findByCliente_Id(idCliente);
    }

    @Override
    public List<Turno> obtenerTurnosPorEmpleado(int idEmpleado) {
        return turnoRepository.findByEmpleado_Id(idEmpleado);
    }

    @Override
    public List<Turno> obtenerTurnosPorServicio(int idServicio) {
        return turnoRepository.findByServicio_Id(idServicio);
    }

    @Override
    public Turno obtenerTurnoPorCodigo(String codigo) {
        return turnoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new TurnoNoEncontradoException("No existe un turno con ese código: " + codigo));
    }

    @Override
    public List<Turno> traerTurnosPorFechaYSucursal(LocalDate fecha, int idSucursal) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findBySucursal_IdAndFechaHoraBetween(idSucursal, inicio, fin);
    }

    @Override
    public List<Turno> traerTurnosPorFechaYServicio(LocalDate fecha, int idServicio) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByServicio_IdAndFechaHoraBetween(idServicio, inicio, fin);
    }

    @Override
    public List<Turno> traerTurnosPorFechaYCliente(LocalDate fecha, int idCliente) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByCliente_IdAndFechaHoraBetween(idCliente, inicio, fin);
    }

    @Override
    public List<Turno> traerTurnosPorFechaYEmpleado(LocalDate fecha, int idEmpleado) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = inicio.plusDays(1);
        return turnoRepository.findByEmpleado_IdAndFechaHoraBetween(idEmpleado, inicio, fin);
    }

    @Override
    public List<Turno> traerTurnos() {
        return turnoRepository.findAll();
    }
}
