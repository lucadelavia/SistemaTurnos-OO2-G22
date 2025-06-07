package com.sistematurnos.repositories;

import com.sistematurnos.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ISucursalRepository extends JpaRepository<Sucursal, Integer> {

	Optional<Sucursal> findById(int id);
	
	Optional<Sucursal> findByTelefono(String telefono);
}