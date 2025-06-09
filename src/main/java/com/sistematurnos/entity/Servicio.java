package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "Servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServicio")
    private int id;

    @Column(name = "nombreServicio", nullable = false)
    private String nombreServicio;

    @Column(nullable = false)
    private int duracion;

    @Column(nullable = false)
    private boolean estado = true;

    public Servicio(String nombreServicio, int duracion) {
        this.nombreServicio = nombreServicio;
        this.duracion = duracion;
        this.estado = true;
    }
}
