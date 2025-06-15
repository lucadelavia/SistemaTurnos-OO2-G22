package com.sistematurnos.repository;

import com.sistematurnos.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;
>>>>>>> 99f4d3c (Version Funcional Spring Security)


import java.util.Optional;
import java.util.List;

public interface ISucursalRepository extends JpaRepository<Sucursal, Integer> {

	Optional<Sucursal> findById(int id);


	Optional<Sucursal> findByTelefono(String telefono);

	@Override
	List<Sucursal> findAll();
	List<Sucursal> findByEstadoTrue();


}