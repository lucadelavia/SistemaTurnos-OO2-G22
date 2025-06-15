package com.sistematurnos.entity;

import com.sistematurnos.entity.enums.Rol;
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

    public Cliente(String nombre, String apellido, String email, String password, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta, Rol rol, int nroCliente) {
        super(nombre, apellido, email, password, direccion, dni, estado, fechaAlta, rol);
        this.nroCliente = nroCliente;
    }
}
