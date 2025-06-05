package com.sistematurnos.entity;

import jakarta.persistence.*;
import lombok.*;

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
}
