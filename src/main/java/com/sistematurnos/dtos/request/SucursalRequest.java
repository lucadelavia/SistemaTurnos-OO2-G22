package com.sistematurnos.dtos.request;

import java.time.LocalTime;

public record SucursalRequest(
        String direccion,
        String telefono,
        LocalTime horaApertura,
        LocalTime horaCierre,
        int espacio,
        int idEstablecimiento
) {}
