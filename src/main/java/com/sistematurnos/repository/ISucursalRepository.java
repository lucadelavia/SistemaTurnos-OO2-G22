package com.sistematurnos.repository;

import com.sistematurnos.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ISucursalRepository extends JpaRepository<Sucursal, Integer> {

	Optional<Sucursal> findById(int id);
	
	Optional<Sucursal> findByTelefono(String telefono);
	
	@Override
	List<Sucursal> findAll();
	List<Sucursal> findByEstadoTrue();
}