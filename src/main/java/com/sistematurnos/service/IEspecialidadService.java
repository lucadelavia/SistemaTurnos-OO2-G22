package com.sistematurnos.service;

import com.sistematurnos.entity.Especialidad;

import java.util.List;

public interface IEspecialidadService {
	public List<Especialidad> traerEspecialidades();
	
	public Especialidad altaEspecialidad(String Nombre);
	
	public Especialidad altaEspecialidad(Especialidad e);
	
	public void bajaEspecialidad(int id);
	
	public Especialidad modificarEspecialidad(Especialidad e);    
	
	public Especialidad obtenerEspecialidadPorNombre(String nombre);
	
	public Especialidad obtenerEspecialidadPorId (int id);

}