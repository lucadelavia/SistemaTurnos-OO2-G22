package com.sistematurnos.repositories;

import com.sistematurnos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByNroCliente(int nroCliente);

    List<Cliente> findByNroClienteGreaterThan(int limite);
}