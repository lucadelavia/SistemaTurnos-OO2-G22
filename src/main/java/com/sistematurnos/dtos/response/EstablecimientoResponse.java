package com.sistematurnos.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstablecimientoResponse {
    private int id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String descripcion;
}