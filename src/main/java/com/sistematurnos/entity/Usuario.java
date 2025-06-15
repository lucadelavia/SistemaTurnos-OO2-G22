package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;
<<<<<<< HEAD

import java.time.LocalDateTime;

=======
import java.time.LocalDateTime;
import com.sistematurnos.entity.enums.Rol;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
>>>>>>> 99f4d3c (Version Funcional Spring Security)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
<<<<<<< HEAD

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
=======
>>>>>>> 99f4d3c (Version Funcional Spring Security)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

<<<<<<< HEAD
=======
    @Column(nullable = false)
    private String password;

>>>>>>> 99f4d3c (Version Funcional Spring Security)
    private String direccion;

    @Column(unique = true, nullable = false)
    private int dni;

    private boolean estado;

    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;

<<<<<<< HEAD
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    public Usuario(String nombre, String apellido, String email, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta, String password, RolUsuario rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
=======
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public Usuario(String nombre, String apellido, String email, String password,
                   String direccion, int dni, boolean estado,
                   LocalDateTime fechaAlta, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
        this.direccion = direccion;
        this.dni = dni;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
<<<<<<< HEAD
        this.password = password;
        this.rol = rol;
    }
=======
        this.rol = rol;
    }

>>>>>>> 99f4d3c (Version Funcional Spring Security)
}
