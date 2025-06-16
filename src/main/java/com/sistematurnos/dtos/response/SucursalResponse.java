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
        Set<?> lstEspecialidad,  // Puede ser Set<Integer> o Set<EspecialidadResponse>
        Set<?> lstDiasDeAtencion  // Puede ser Set<Integer> o Set<DiasDeAtencionResponse>
) {}