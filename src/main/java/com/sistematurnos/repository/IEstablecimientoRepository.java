package com.sistematurnos.repository;

import com.sistematurnos.entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {


	Optional<Establecimiento> findByNombre(String nombre);

	Optional<Establecimiento> findByCuit(String cuit);

	List<Establecimiento> findByNombreContainingIgnoreCase(String fragmento);

}
