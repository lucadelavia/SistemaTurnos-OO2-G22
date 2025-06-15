package com.sistematurnos.repository;

import com.sistematurnos.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ITurnoRepository extends JpaRepository<Turno, Integer> {

    List<Turno> findByCliente_Id(int idCliente);
    List<Turno> findByEmpleado_Id(int idEmpleado);
    List<Turno> findBySucursal_Id(int idSucursal);
    List<Turno> findByServicio_Id(int idServicio);
    Optional<Turno> findByCodigo(String codigo);

    List<Turno> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Turno> findByFechaHoraBetweenAndEstado(LocalDateTime inicio, LocalDateTime fin, boolean estado);

    List<Turno> findByCliente_IdAndFechaHoraBetween(int idCliente, LocalDateTime inicio, LocalDateTime fin);
    List<Turno> findByEmpleado_IdAndFechaHoraBetween(int idEmpleado, LocalDateTime inicio, LocalDateTime fin);
    List<Turno> findBySucursal_IdAndFechaHoraBetween(int idSucursal, LocalDateTime inicio, LocalDateTime fin);
    List<Turno> findByServicio_IdAndFechaHoraBetween(int idServicio, LocalDateTime inicio, LocalDateTime fin);

}
