package com.sistematurnos.service;

import com.sistematurnos.entity.Cliente;

import java.util.List;

public interface IClienteService {
    public List<Cliente> traerClientes();

    public Cliente altaCliente(String nombre, String apellido, String email, String direccion,
                            int dni, String password);
    
    public Cliente altaCliente(Cliente cliente);

    public Cliente obtenerClientePorId(int id);

    public Cliente traerClientePorNroCliente(int nroCliente);

    public List<Cliente> findByNroClienteGreaterThan(int limite);

    public void bajaCliente(int id);

    public Cliente modificarCliente(Cliente c);
}
