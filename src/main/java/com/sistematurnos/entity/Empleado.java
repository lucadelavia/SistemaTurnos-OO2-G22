package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "empleados")
public class Empleado extends Usuario {

    private int cuil;
    private String matricula;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "empleado_especialidad",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> lstEspecialidades;
}
