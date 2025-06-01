package com.sistematurnos.entity;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode


public class Establecimiento {

    private int id;
    private String nombre;
    private String cuit;
    private String direccion;
    private String descripcion;
    private Set<Sucursal> sucursales;


}
