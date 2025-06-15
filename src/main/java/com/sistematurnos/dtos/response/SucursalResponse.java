package com.sistematurnos.dtos.response;

import com.sistematurnos.dtos.response.simple.DiaDeAtencionSimple;
import com.sistematurnos.dtos.response.simple.EspecialidadSimple;

import java.time.LocalTime;
import java.util.Set;

public record SucursalResponse(
        int id,
        String direccion,
        String telefono,
        LocalTime horaApertura,
        LocalTime horaCierre,
        int espacio,
        boolean estado,
        String establecimientoNombre,
        Set<EspecialidadSimple> especialidades,
        Set<DiaDeAtencionSimple> dias
) {}
