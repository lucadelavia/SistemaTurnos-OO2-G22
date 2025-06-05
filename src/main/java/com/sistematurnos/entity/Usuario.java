package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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
    private String email;
    private String direccion;

    @Column(unique = true, nullable = false)
    private int dni;

    private boolean estado;

    @Column(name = "fechaAlta")
    private LocalDateTime fechaAlta;
}
