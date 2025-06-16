package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.TurnoRequest;
import com.sistematurnos.dtos.response.TurnoResponse;
import com.sistematurnos.entity.*;

public class TurnoMapper {

    public static Turno toEntity(TurnoRequest dto, Cliente cliente, Empleado empleado, Sucursal sucursal, Servicio servicio) {
        Turno turno = new Turno();
        turno.setFechaHora(dto.fechaHora());
        turno.setEstado(dto.estado());
        turno.setCodigo(dto.codigo());
        turno.setCliente(cliente);
        turno.setEmpleado(empleado);
        turno.setSucursal(sucursal);
        turno.setServicio(servicio);
        return turno;
    }

    public static TurnoResponse toResponse(Turno turno) {
        return new TurnoResponse(
                turno.getId(),
                turno.getFechaHora(),
                turno.isEstado(),
                turno.getCodigo(),
                turno.getCliente().getId(),
                turno.getCliente().getNombre(),
                turno.getEmpleado().getId(),
                turno.getEmpleado().getNombre(),
                turno.getSucursal().getId(),
                turno.getSucursal().getDireccion(),
                turno.getServicio().getId(),
                turno.getServicio().getNombreServicio()
        );
    }
}
