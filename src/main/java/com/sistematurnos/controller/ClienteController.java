package com.sistematurnos.controller;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.service.IClienteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con los clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @Operation(summary = "Listar todos los clientes activos")
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.traerClientes()
                .stream()
                .filter(Cliente::isEstado)
                .toList();
    }

    @Operation(summary = "Obtener un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Obtener un cliente por su número de cliente")
    @GetMapping("/nroCliente/{nro}")
    public ResponseEntity<Cliente> obtenerPorNroCliente(@PathVariable int nro) {
        Cliente cliente = clienteService.traerClientePorNroCliente(nro);
        return ResponseEntity.ok(cliente);
    }

    @Operation(summary = "Listar clientes con número de cliente mayor a un valor dado")
    @GetMapping("/mayorNroCliente/{limite}")
    public List<Cliente> clientesConNroClienteMayorA(@PathVariable int limite) {
        return clienteService.findByNroClienteGreaterThan(limite);
    }

    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.altaCliente(cliente);
        return ResponseEntity.ok(nuevo);
    }

    @Operation(summary = "Modificar un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        Cliente actualizado = clienteService.modificarCliente(cliente);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(summary = "Dar de baja (eliminar lógicamente) a un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        clienteService.bajaCliente(id);
        return ResponseEntity.noContent().build();
    }
}
