package com.sistematurnos.repository;

import com.sistematurnos.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

    List<Turno> findByClienteId(int idCliente);
    List<Turno> findByEmpleadoId(int idEmpleado);
    List<Turno> findBySucursalId(int idSucursal);
    List<Turno> findByServicioId(int idServicio);

    List<Turno> findByFechaHoraBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Turno> findByFechaHoraBetweenAndEstado(LocalDateTime desde, LocalDateTime hasta, boolean estado);

    List<Turno> findByFechaHoraBetweenAndClienteId(LocalDateTime desde, LocalDateTime hasta, int idCliente);
    List<Turno> findByFechaHoraBetweenAndEmpleadoId(LocalDateTime desde, LocalDateTime hasta, int idEmpleado);
    List<Turno> findByFechaHoraBetweenAndSucursalId(LocalDateTime desde, LocalDateTime hasta, int idSucursal);
    List<Turno> findByFechaHoraBetweenAndServicioId(LocalDateTime desde, LocalDateTime hasta, int idServicio);
}
