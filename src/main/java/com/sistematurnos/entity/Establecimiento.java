package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter 
@Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "establecimientos")
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestablecimiento")
    private int id;

    @Column(nullable = false, unique = true)
    private String nombre;
    
    @Column(nullable = false, unique = true)
    private String cuit;
    
    @Column(nullable = false)
    private String direccion; 
    
    private String descripcion;
    
    public Establecimiento(String nombre, String cuit, String direccion, String descripcion) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }
}