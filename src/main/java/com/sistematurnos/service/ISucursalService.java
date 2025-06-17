package com.sistematurnos.service;

import com.sistematurnos.dtos.request.SucursalRequest;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Sucursal;

import java.util.List;

public interface ISucursalService {

    Sucursal altaSucursal(SucursalRequest dto);

    public void bajaSucursal(int id);

    Sucursal modificarSucursal(int id, SucursalRequest dto);

    public Sucursal modificarSucursal(Sucursal suc);

    public Sucursal traer(int idSucursal);

    public List<Sucursal> traerSucursales();

    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion);

    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion);

    public void asociarEspecialidad(int idSucursal, Especialidad especialidad);

    public void removerEspecialidad(int idSucursal, Especialidad especialidad);

    public Sucursal guardar(Sucursal suc);
}
