package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "diasDeAtencion")
public class DiasDeAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDiasDeAtencion")
    private int id;

    private String nombre;

    public DiasDeAtencion(String nombre) {
        this.nombre = nombre;
    }


}
