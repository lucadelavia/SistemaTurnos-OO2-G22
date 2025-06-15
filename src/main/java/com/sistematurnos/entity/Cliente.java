package com.sistematurnos.entity;

<<<<<<< HEAD
=======
import com.sistematurnos.entity.enums.Rol;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
<<<<<<< HEAD
@Getter
@Setter
=======
@Getter @Setter
>>>>>>> 99f4d3c (Version Funcional Spring Security)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "clientes")
public class Cliente extends Usuario {

    @Column(name = "nroCliente")
    private int nroCliente;

<<<<<<< HEAD
    public Cliente(String nombre, String apellido, String email, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta,
                   String password, RolUsuario rol,
                   int nroCliente) {
        super(nombre, apellido, email, direccion, dni, estado, fechaAlta, password, rol);
=======
    public Cliente(String nombre, String apellido, String email, String password, String direccion,
                   int dni, boolean estado, LocalDateTime fechaAlta, Rol rol, int nroCliente) {
        super(nombre, apellido, email, password, direccion, dni, estado, fechaAlta, rol);
>>>>>>> 99f4d3c (Version Funcional Spring Security)
        this.nroCliente = nroCliente;
    }
}
