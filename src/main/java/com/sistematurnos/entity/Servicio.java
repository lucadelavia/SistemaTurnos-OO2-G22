package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "servicios")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEspecialidad", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Especialidad especialidad;

    public Servicio(String nombreServicio, int duracion, Especialidad especialidad) {
        this.nombreServicio = nombreServicio;
        this.duracion = duracion;
        this.especialidad = especialidad;
        this.estado = true;
    }
}
