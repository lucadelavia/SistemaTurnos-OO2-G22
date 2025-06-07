package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(name = "nroCliente")
    private int nroCliente;


    public Cliente(String nombre, String apellido, String email, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta, int nroCliente) {
        super(nombre, apellido, email, direccion, dni, estado, fechaAlta);
        this.nroCliente = nroCliente;
    }




}
