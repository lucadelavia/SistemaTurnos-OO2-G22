package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "empleados")
public class Empleado extends Usuario {

    private long cuil;
    private String matricula;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "empleado_especialidad",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> lstEspecialidades;

    public Empleado(String nombre, String apellido, String email, String direccion,
                    int dni, boolean estado, LocalDateTime fechaAlta,
                    String password, RolUsuario rol,
                    long cuil, String matricula) {
        super(nombre, apellido, email, direccion, dni, estado, fechaAlta, password, rol);
        this.cuil = cuil;
        this.matricula = matricula;
    }
}
