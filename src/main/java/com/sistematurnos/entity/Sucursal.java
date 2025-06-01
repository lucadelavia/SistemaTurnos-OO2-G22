package com.sistematurnos.entity;

import lombok.*;

import java.sql.Time;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode


public class Sucursal {

    private int id;
    private String direccion;
    private String telefono;
    private Time horaApertura;
    private Time horaCierre;
    private Integer espacio;
    private Set<Especialidad> lstEspecialidad;
    private Set<DiasDeAtencion> lstDiasDeAtencion;
    private Establecimiento establecimiento;

}
