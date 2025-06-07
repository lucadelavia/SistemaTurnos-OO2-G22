package com.sistematurnos.entity;

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
    
    public Sucursal(String direccion, String telefono, LocalTime horaApertura, LocalTime horaCierre, int espacio) {
        this.direccion = direccion;
        this.telefono = telefono;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.espacio = espacio;
    }
}