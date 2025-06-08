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
@Table(name = "Turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idTurno")
    private int id;

    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;

    @Column(name = "estadoActivo")
    private boolean estado;

    private String codigo;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "servicio_id", nullable = false)
    private Servicio servicio;

    public Turno(LocalDateTime fechaHora, boolean estado, String codigo,
                 Servicio servicio, Cliente cliente, Sucursal sucursal, Empleado empleado) {
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.codigo = codigo;
        this.servicio = servicio;
        this.cliente = cliente;
        this.sucursal = sucursal;
        this.empleado = empleado;
    }
}
