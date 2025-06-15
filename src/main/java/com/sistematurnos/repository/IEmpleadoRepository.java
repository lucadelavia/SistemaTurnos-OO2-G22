package com.sistematurnos.repository;

import com.sistematurnos.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {

	Optional<Empleado> findByDni(int dni);         // heredado de Usuario
	Optional<Empleado> findByCuil(long cuil);
	Optional<Empleado> findByMatricula(String matricula);
	List<Empleado> findByEstadoTrue();

	List<Empleado> findByLstEspecialidadesNombre(String nombre);
}
