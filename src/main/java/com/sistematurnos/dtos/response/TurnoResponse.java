package com.sistematurnos.dtos.response;

import java.time.LocalDateTime;

public record TurnoResponse(
        Integer id,
        LocalDateTime fechaHora,
        boolean estadoActivo,
        String codigo,
        Object cliente,  // Puede ser Integer o ClienteResponse
        Object empleado, // Puede ser Integer o EmpleadoResponse
        Object sucursal, // Puede ser Integer o SucursalResponse
        Object servicio  // Puede ser Integer o ServicioResponse
) {}