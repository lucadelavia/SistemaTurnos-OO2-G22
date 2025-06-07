package com.sistematurnos.repositories;

import com.sistematurnos.entity.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IEstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {

	Optional<Establecimiento> findById(int id);
	
	Optional<Establecimiento> findByNombreEstablecimiento(String nombre);

	Optional<Establecimiento> findByCuitEstablecimiento(String cuit);
}