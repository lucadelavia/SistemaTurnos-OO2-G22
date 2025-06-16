package com.sistematurnos.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablecimientoRequest {
    private int id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String descripcion;
}