package com.sistematurnos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idsucursal")
    private int id;

    private String direccion;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(name = "horarioApertura")
    private LocalTime horaApertura;

    @Column(name = "horarioCierre")
    private LocalTime horaCierre;

    private int espacio;

    @Column(nullable = false)
    private boolean estado = true;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idestablecimiento")
    private Establecimiento establecimiento;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "especialidad_sucursal",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private Set<Especialidad> lstEspecialidad;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "diasDeAtencion_sucursal",
            joinColumns = @JoinColumn(name = "sucursal_id"),
            inverseJoinColumns = @JoinColumn(name = "diasDeAtencion_id")
    )
    private Set<DiasDeAtencion> lstDiasDeAtencion;
}
