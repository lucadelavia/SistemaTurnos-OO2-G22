package com.sistematurnos.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistematurnos.entities.Empleado;


@Repository("empleadoRepository")
public interface IEmpleadoRepository extends JpaRepository<Empleado, Serializable> {

	public Empleado findById(int id);

	public Empleado findByDni(int dni);

	public Empleado findByCuil(int cuil);

	public List<Empleado> findByEspecialidad(String nombreEsp);

	public Empleado findByMatricula(String matricula);
}
