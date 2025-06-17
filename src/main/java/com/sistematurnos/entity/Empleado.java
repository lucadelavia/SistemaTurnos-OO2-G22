package com.sistematurnos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sistematurnos.entity.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "empleados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Usuario {

    private long cuil;
    private String matricula;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "empleado_especialidad",
            joinColumns = @JoinColumn(name = "empleado_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Especialidad> lstEspecialidades = new HashSet<>();

    public Empleado(String nombre, String apellido, String email, String password, String direccion,
                    int dni, boolean estado, LocalDateTime fechaAlta, Rol rol,
                    long cuil, String matricula) {
        super(nombre, apellido, email, password, direccion, dni, estado, fechaAlta, rol);
        this.cuil = cuil;
        this.matricula = matricula;
    }
}
