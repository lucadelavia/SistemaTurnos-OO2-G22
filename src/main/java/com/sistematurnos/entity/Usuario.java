package com.sistematurnos.entity;

import com.sistematurnos.entity.enums.Rol;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer id;

    private String nombre;
    private String apellido;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String direccion;

    @Column(unique = true, nullable = false)
    private int dni;

    private boolean estado;

    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    public Usuario(String nombre, String apellido, String email, String password,
                   String direccion, int dni, boolean estado, LocalDateTime fechaAlta, Rol rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.dni = dni;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.rol = rol;
    }
}
