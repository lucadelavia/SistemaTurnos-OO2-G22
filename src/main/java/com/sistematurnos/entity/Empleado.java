package com.sistematurnos.entity;

import java.util.Set;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Empleado extends Usuario{

    private int cuil;
    private String matricula;
    private Set<Especialidad> lstEspecialidades;

}
