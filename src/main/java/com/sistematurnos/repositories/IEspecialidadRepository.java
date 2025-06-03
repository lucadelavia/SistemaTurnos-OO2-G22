package com.sistematurnos.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sistematurnos.entities.Especialidad;


@Repository("especialidadRepository")
public interface IEspecialidadRepository extends JpaRepository<Especialidad, Serializable> {

	public Especialidad findById(int id);

	public Especialidad findByNombre(String nombre);
}
