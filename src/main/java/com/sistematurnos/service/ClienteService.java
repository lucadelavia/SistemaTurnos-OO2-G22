package com.sistematurnos.service;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.exception.ClienteDuplicadoException;
import com.sistematurnos.exception.ClienteNoEncontradoException;
import com.sistematurnos.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Cliente altaCliente(Cliente cliente) {
        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new ClienteDuplicadoException("Ya existe un cliente con ese DNI.");
        }

        cliente.setPassword(encoder.encode(cliente.getPassword()));
        cliente.setRol(Rol.CLIENTE);
        cliente.setEstado(true);
        cliente.setFechaAlta(LocalDateTime.now());

        return clienteRepository.save(cliente);
    }

    public Cliente obtenerClientePorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("ERROR: No existe el cliente solicitado con ID " + id));
    }

    public void bajaCliente(int id) {
        Cliente cliente = obtenerClientePorId(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    public Cliente modificarCliente(Cliente c) {
        Cliente actual = obtenerClientePorId(c.getId());

        actual.setNombre(c.getNombre());
        actual.setApellido(c.getApellido());
        actual.setEmail(c.getEmail());
        actual.setDireccion(c.getDireccion());
        actual.setDni(c.getDni());
        actual.setEstado(c.isEstado());
        actual.setNroCliente(c.getNroCliente());

        return clienteRepository.save(actual);
    }

    public List<Cliente> traerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente traerClientePorNroCliente(int nroCliente) {
        return clienteRepository.findByNroCliente(nroCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException("No se encontró cliente con número " + nroCliente));
    }

    public List<Cliente> findByNroClienteGreaterThan(int limite) {
        return clienteRepository.findByNroClienteGreaterThan(limite);
    }
}
