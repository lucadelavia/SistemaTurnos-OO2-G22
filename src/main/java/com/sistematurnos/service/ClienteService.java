package com.sistematurnos.service;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public Cliente altaCliente(String nombre, String apellido, String email, String direccion,
                               int dni, boolean estado, LocalDateTime fechaAlta, int nroCliente) {

        if (clienteRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI.");
        }

        Cliente cliente = new Cliente(nombre, apellido, email, direccion, dni, estado, fechaAlta, nroCliente);
        return clienteRepository.save(cliente);
    }

    public Cliente altaCliente(Cliente cliente) {
        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un cliente con ese DNI.");
        }
        return clienteRepository.save(cliente);
    }

    public Cliente obtenerClientePorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe el cliente solicitado"));
    }

    public void bajaCliente(int id) {
        Cliente cliente = obtenerClientePorId(id);
        clienteRepository.delete(cliente);
    }

    public Cliente modificarCliente(Cliente c) {
        Cliente actual = clienteRepository.findById(c.getId())
                .orElseThrow(() -> new IllegalArgumentException("ERROR: No existe el cliente solicitado"));

        actual.setNombre(c.getNombre());
        actual.setApellido(c.getApellido());
        actual.setEmail(c.getEmail());
        actual.setDireccion(c.getDireccion());
        actual.setDni(c.getDni());
        actual.setEstado(c.isEstado());
        actual.setNroCliente(c.getNroCliente());

        try {
            return clienteRepository.save(actual);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("No se pudo guardar el cliente: " + e.getMessage());
        }
    }

    public List<Cliente> traerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente traerClientePorNroCliente(int nroCliente) {
        return clienteRepository.findByNroCliente(nroCliente)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró cliente con nroCliente: " + nroCliente));
    }

    public List<Cliente> findByNroClienteGreaterThan(int limite) {
        return clienteRepository.findByNroClienteGreaterThan(limite);
    }
}
