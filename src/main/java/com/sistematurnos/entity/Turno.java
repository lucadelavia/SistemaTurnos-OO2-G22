package com.sistematurnos.entity;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class Turno {

    private int id;
    private LocalDateTime fechaHora;
    private boolean estado;
    private String codigo;
    private Servicio servicio;
    private Cliente cliente;
    private Empleado empleado;
    private Sucursal sucursal;

}
