package com.sistematurnos.repository;

import com.sistematurnos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByNroCliente(int nroCliente);

    List<Cliente> findByNroClienteGreaterThan(int limite);

    Optional<Cliente> findByDni(int dni);

    List<Cliente> findByApellidoContainingIgnoreCase(String apellido); // útil para búsquedas parciales
}

