package com.sistematurnos.dtos.request;

import java.time.LocalDateTime;

public record TurnoRequest(
        LocalDateTime fechaHora,
        boolean estado,
        String codigo,
        int idCliente,
        int idEmpleado,
        int idSucursal,
        int idServicio
) {}
