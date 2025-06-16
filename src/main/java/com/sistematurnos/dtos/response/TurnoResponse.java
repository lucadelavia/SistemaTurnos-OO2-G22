package com.sistematurnos.dtos.response;

import java.time.LocalDateTime;

public record TurnoResponse(
        Integer id,
        LocalDateTime fechaHora,
        boolean estadoActivo,
        String codigo,

        Integer clienteId,
        String clienteNombre,

        Integer empleadoId,
        String empleadoNombre,

        Integer sucursalId,
        String sucursalDireccion,

        Integer servicioId,
        String servicioNombre
) {}
