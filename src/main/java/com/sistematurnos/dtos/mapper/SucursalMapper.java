package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.response.SucursalResponse;
import com.sistematurnos.dtos.response.simple.DiaDeAtencionSimple;
import com.sistematurnos.dtos.response.simple.EspecialidadSimple;
import com.sistematurnos.entity.Sucursal;

import java.util.Set;
import java.util.stream.Collectors;

public class SucursalMapper {

    public static SucursalResponse toResponse(Sucursal s) {
        Set<EspecialidadSimple> especialidades = s.getLstEspecialidad().stream()
                .map(e -> new EspecialidadSimple(e.getId(), e.getNombre()))
                .collect(Collectors.toSet());

        Set<DiaDeAtencionSimple> dias = s.getLstDiasDeAtencion().stream()
                .map(d -> new DiaDeAtencionSimple(d.getId(), d.getNombre()))
                .collect(Collectors.toSet());

        return new SucursalResponse(
                s.getId(),
                s.getDireccion(),
                s.getTelefono(),
                s.getHoraApertura(),
                s.getHoraCierre(),
                s.getEspacio(),
                s.isEstado(),
                s.getEstablecimiento().getNombre(),
                especialidades,
                dias
        );
    }
}
