package com.sistematurnos.repository;

import com.sistematurnos.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IServicioRepository extends JpaRepository<Servicio, Integer> {

	Optional<Servicio> findByNombreServicio(String nombreServicio);

	List<Servicio> findByNombreServicioContainingIgnoreCase(String fragmento);

}
