package com.sistematurnos.dtos.request;

public record ClienteRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String direccion,
        int dni,
        int nroCliente
) {}
