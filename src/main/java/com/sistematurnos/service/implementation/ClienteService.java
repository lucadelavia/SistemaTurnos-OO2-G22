package com.sistematurnos.service.implementation;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.entity.enums.Rol;
import com.sistematurnos.exception.ClienteDuplicadoException;
import com.sistematurnos.exception.ClienteNoEncontradoException;
import com.sistematurnos.repository.IClienteRepository;
import com.sistematurnos.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Cliente altaCliente(String nombre, String apellido, String email, String direccion, int dni, String password) {
        if (clienteRepository.findByDni(dni).isPresent()) {
            throw new ClienteDuplicadoException("Ya existe un cliente con ese DNI.");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setEmail(email);
        cliente.setDireccion(direccion);
        cliente.setDni(dni);
        cliente.setEstado(true);
        cliente.setFechaAlta(LocalDateTime.now());
        cliente.setPassword(passwordEncoder.encode(password));
        cliente.setRol(Rol.CLIENTE);

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente altaCliente(Cliente cliente) {
        if (clienteRepository.findByDni(cliente.getDni()).isPresent()) {
            throw new ClienteDuplicadoException("Ya existe un cliente con ese DNI.");
        }

        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente obtenerClientePorId(int id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNoEncontradoException("ERROR: No existe el cliente solicitado con ID " + id));
    }

    @Override
    public void bajaCliente(int id) {
        Cliente cliente = obtenerClientePorId(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente modificarCliente(Cliente c) {
        Cliente actual = clienteRepository.findById(c.getId())
                .orElseThrow(() -> new ClienteNoEncontradoException("ERROR: No se encontró el cliente con ID " + c.getId()));

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

    @Override
    public List<Cliente> traerClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente traerClientePorNroCliente(int nroCliente) {
        return clienteRepository.findByNroCliente(nroCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException("No se encontró cliente con número " + nroCliente));
    }

    @Override
    public List<Cliente> findByNroClienteGreaterThan(int limite) {
        return clienteRepository.findByNroClienteGreaterThan(limite);
    }
}
