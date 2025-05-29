package com.sistematurnos.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String direccion;
    private int dni;
    private boolean estado;
    private LocalDateTime fechaAlta;

}
