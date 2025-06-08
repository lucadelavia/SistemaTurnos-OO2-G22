package com.sistematurnos.repository;

import com.sistematurnos.entity.DiasDeAtencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface IDiasDeAtencionRepository extends JpaRepository<DiasDeAtencion, Integer> {

    Optional<DiasDeAtencion> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<DiasDeAtencion> findByNombreContainingIgnoreCase(String parteNombre);
}
