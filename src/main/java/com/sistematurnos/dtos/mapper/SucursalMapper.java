package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.SucursalRequest;
import com.sistematurnos.dtos.response.DiasDeAtencionResponse;
import com.sistematurnos.dtos.response.EspecialidadResponse;
import com.sistematurnos.dtos.response.SucursalResponse;
import com.sistematurnos.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

public class SucursalMapper {

    public static Sucursal toEntity(SucursalRequest dto) {
        Sucursal sucursal = new Sucursal();
        sucursal.setDireccion(dto.direccion());
        sucursal.setTelefono(dto.telefono());
        sucursal.setHoraApertura(dto.horaApertura());
        sucursal.setHoraCierre(dto.horaCierre());
        sucursal.setEspacio(dto.espacio());
        sucursal.setEstado(dto.estado());
        return sucursal;
    }

    public static SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(
                sucursal.getId(),
                sucursal.getDireccion(),
                sucursal.getTelefono(),
                sucursal.getHoraApertura(),
                sucursal.getHoraCierre(),
                sucursal.getEspacio(),
                sucursal.isEstado(),
                sucursal.getEstablecimiento() != null ? sucursal.getEstablecimiento().getId() : null,
                sucursal.getEstablecimiento() != null ? sucursal.getEstablecimiento().getNombre() : "-", // 👈 este campo nuevo
                sucursal.getLstEspecialidad() != null ?
                        sucursal.getLstEspecialidad().stream()
                                .map(e -> new EspecialidadResponse(e.getId(), e.getNombre()))
                                .collect(Collectors.toSet())
                        : Set.of(),
                sucursal.getLstDiasDeAtencion() != null ?
                        sucursal.getLstDiasDeAtencion().stream()
                                .map(d -> new DiasDeAtencionResponse(d.getId(), d.getNombre()))
                                .collect(Collectors.toSet())
                        : Set.of()
        );
    }

}
