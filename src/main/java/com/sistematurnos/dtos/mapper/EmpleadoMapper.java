package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.EmpleadoRequest;
import com.sistematurnos.dtos.response.EmpleadoResponse;
import com.sistematurnos.entity.Empleado;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.enums.Rol;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class EmpleadoMapper {

    public static Empleado toEntity(EmpleadoRequest dto, Set<Especialidad> especialidades) {
        Empleado empleado = new Empleado();
        empleado.setNombre(dto.nombre());
        empleado.setApellido(dto.apellido());
        empleado.setEmail(dto.email());
        empleado.setPassword(dto.password());
        empleado.setDireccion(dto.direccion());
        empleado.setDni(dto.dni());
        empleado.setCuil(dto.cuil());
        empleado.setMatricula(dto.matricula());
        empleado.setFechaAlta(LocalDateTime.now());
        empleado.setEstado(true);
        empleado.setRol(Rol.EMPLEADO);

        empleado.setLstEspecialidades(especialidades);
        return empleado;
    }

    public static EmpleadoResponse toResponse(Empleado e) {
        Set<String> nombresEspecialidades = e.getLstEspecialidades().stream()
                .map(Especialidad::getNombre)
                .collect(Collectors.toSet());

        return new EmpleadoResponse(
                e.getId(),
                e.getNombre(),
                e.getApellido(),
                e.getEmail(),
                e.getDireccion(),
                e.getDni(),
                e.isEstado(),
                e.getFechaAlta(),
                e.getRol(),
                e.getCuil(),
                e.getMatricula(),
                nombresEspecialidades
        );
    }
}
