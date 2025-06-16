package com.sistematurnos.dtos.request;

import java.util.Set;

public record EmpleadoRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String direccion,
        int dni,
        long cuil,
        String matricula,
        Set<Integer> especialidadesIds
) {}
