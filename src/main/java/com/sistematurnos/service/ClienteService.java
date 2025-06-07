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

        Cliente c = new Cliente(nombre, apellido, email, direccion, dni, estado, fechaAlta, nroCliente);

        if (clienteRepository.findById(c.getId()).isPresent()) {
            throw new IllegalArgumentException("Este cliente ya existe.");
        }

        return clienteRepository.save(c);
    }

    public Cliente altaCliente(Cliente cliente) {
        if (clienteRepository.findById(cliente.getId()).isPresent()) {
            throw new IllegalArgumentException("Este cliente ya existe.");
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
                .orElseThrow(() -> new IllegalArgumentException("No se encontró cliente con nroCliente: " + nroCliente));
    }

    public List<Cliente> findByNroClienteGreaterThan(int limite) {
        return clienteRepository.findByNroClienteGreaterThan(limite);
    }

}
