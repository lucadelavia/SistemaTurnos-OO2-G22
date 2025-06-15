package com.sistematurnos.dtos.request;

import com.sistematurnos.entity.enums.Rol;

public record ClienteRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String direccion,
        int dni,
        Rol rol,
        int nroCliente
) {}
