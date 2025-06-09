package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int id;

    private String nombre;
    private String apellido;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String direccion;

    @Column(unique = true, nullable = false)
    private int dni;

    private boolean estado;

    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;

    public Usuario(String nombre, String apellido, String email, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
        this.dni = dni;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
    }
}