package com.sistematurnos.dtos.request;

import java.time.LocalTime;
import java.util.Set;

public record SucursalRequest(
        String direccion,
        String telefono,
        LocalTime horaApertura,
        LocalTime horaCierre,
        int espacio,
        boolean estado,
        int idEstablecimiento,
        Set<Integer> idEspecialidades,
        Set<Integer> idDiasDeAtencion
) {}
