package com.sistematurnos.repository;

import com.sistematurnos.entity.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface IEspecialidadRepository extends JpaRepository<Especialidad, Integer> {

	Optional<Especialidad> findByNombre(String nombre);

	List<Especialidad> findByNombreContainingIgnoreCase(String nombreParcial); // búsqueda parcial
}
