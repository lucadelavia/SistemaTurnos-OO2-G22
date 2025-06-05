package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "establecimientos")
public class Establecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestablecimiento")
    private int id;

    private String nombre;
    private String cuit;
    private String direccion;
    private String descripcion;

    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sucursal> sucursales;
}
