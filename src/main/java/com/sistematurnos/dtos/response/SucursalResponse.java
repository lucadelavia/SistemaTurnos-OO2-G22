package com.sistematurnos.dtos.response;

import java.time.LocalTime;
import java.util.Set;

public record SucursalResponse(
        Integer id,
        String direccion,
        String telefono,
        LocalTime horaApertura,
        LocalTime horaCierre,
        int espacio,
        boolean estado,
        Integer idEstablecimiento,
        Set<EspecialidadResponse> lstEspecialidad,
        Set<DiasDeAtencionResponse> lstDiasDeAtencion
) {}
