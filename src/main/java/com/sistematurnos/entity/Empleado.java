package com.sistematurnos.entity;

<<<<<<< HEAD
=======
import com.sistematurnos.entity.enums.Rol;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Getter
@Setter
=======
@Getter @Setter
>>>>>>> 99f4d3c (Version Funcional Spring Security)
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

<<<<<<< HEAD
    public Empleado(String nombre, String apellido, String email, String direccion,
                    int dni, boolean estado, LocalDateTime fechaAlta,
                    String password, RolUsuario rol,
                    long cuil, String matricula) {
        super(nombre, apellido, email, direccion, dni, estado, fechaAlta, password, rol);
=======
    public Empleado(String nombre, String apellido, String email, String password, String direccion,
                    int dni, boolean estado, LocalDateTime fechaAlta, Rol rol,
                    long cuil, String matricula) {
        super(nombre, apellido, email, password, direccion, dni, estado, fechaAlta, rol);
>>>>>>> 99f4d3c (Version Funcional Spring Security)
        this.cuil = cuil;
        this.matricula = matricula;
    }
}
