package com.sistematurnos.dtos.request;

import com.sistematurnos.entity.enums.Rol;
import java.util.Set;

public record EmpleadoRequest(
        String nombre,
        String apellido,
        String email,
        String password,
        String direccion,
        int dni,
        Rol rol,
        long cuil,
        String matricula,
        Set<Integer> especialidadesIds
) {}
