package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.ServicioRequest;
import com.sistematurnos.dtos.response.ServicioResponse;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Servicio;

public class ServicioMapper {

    public static ServicioResponse toResponse(Servicio servicio) {
        return new ServicioResponse(
            servicio.getId(),
            servicio.getNombreServicio(),
            servicio.getDuracion(),
            servicio.isEstado(),
            servicio.getEspecialidad().getId(),
            servicio.getEspecialidad().getNombre()
        );
    }

    public static Servicio toEntity(ServicioRequest request, Especialidad especialidad) {
        Servicio servicio = new Servicio();
        servicio.setNombreServicio(request.nombreServicio());
        servicio.setDuracion(request.duracion());
        servicio.setEspecialidad(especialidad);
        servicio.setEstado(true);
        return servicio;
    }
}