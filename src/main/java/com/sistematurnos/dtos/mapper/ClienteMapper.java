package com.sistematurnos.dtos.mapper;

import com.sistematurnos.dtos.request.ClienteRequest;
import com.sistematurnos.dtos.response.ClienteResponse;
import com.sistematurnos.entity.Cliente;
import com.sistematurnos.entity.enums.Rol;

import java.time.LocalDateTime;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequest dto) {
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.nombre());
        cliente.setApellido(dto.apellido());
        cliente.setEmail(dto.email());
        cliente.setPassword(dto.password());
        cliente.setDireccion(dto.direccion());
        cliente.setDni(dto.dni());
        cliente.setNroCliente(dto.nroCliente());
        cliente.setRol(Rol.CLIENTE);

        cliente.setEstado(true);
        cliente.setFechaAlta(LocalDateTime.now());
        return cliente;
    }

    public static ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(
                c.getId(),
                c.getNombre(),
                c.getApellido(),
                c.getEmail(),
                c.getDireccion(),
                c.getDni(),
                c.isEstado(),
                c.getFechaAlta(),
                c.getRol(),
                c.getNroCliente()
        );
    }
}
