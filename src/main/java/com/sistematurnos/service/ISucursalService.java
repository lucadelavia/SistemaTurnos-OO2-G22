package com.sistematurnos.service;

import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Sucursal;

import java.time.LocalTime;
import java.util.List;


public interface ISucursalService {
    public Sucursal altaSucursal(String direccion, String telefono, LocalTime horaApertura, LocalTime horaCierre, int espacio);

    public Sucursal altaSucursal(Sucursal suc);
    
    public void bajaSucursal(int id);

    public Sucursal modificarSucursal(Sucursal suc);

    public Sucursal traer(int idSucursal);

    public List<Sucursal> traerSucursales();

    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion);

    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion);

    public void asociarEspecialidad(int idSucursal, Especialidad especialidad);

    public void removerEspecialidad(int idSucursal, Especialidad especialidad);

    public Sucursal guardar(Sucursal suc);
}
