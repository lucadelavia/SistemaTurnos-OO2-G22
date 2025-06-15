package com.sistematurnos.dtos.response;

import java.time.LocalDateTime;

public record TurnoResponse(
        int id,
        LocalDateTime fechaHora,
        boolean estado,
        String codigo,
        String nombreCliente,
        String nombreEmpleado,
        String nombreServicio,
        String direccionSucursal
) {}
