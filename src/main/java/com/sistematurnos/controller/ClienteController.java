package com.sistematurnos.controller;

import com.sistematurnos.entity.Cliente;
import com.sistematurnos.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.traerClientes()
                .stream()
                .filter(Cliente::isEstado)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        try {
            Cliente cliente = clienteService.obtenerClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nroCliente/{nro}")
    public ResponseEntity<Cliente> obtenerPorNroCliente(@PathVariable int nro) {
        try {
            Cliente cliente = clienteService.traerClientePorNroCliente(nro);
            return ResponseEntity.ok(cliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/mayorNroCliente/{limite}")
    public List<Cliente> clientesConNroClienteMayorA(@PathVariable int limite) {
        return clienteService.findByNroClienteGreaterThan(limite);
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        try {
            Cliente nuevo = clienteService.altaCliente(cliente);
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        try {
            cliente.setId(id);
            Cliente actualizado = clienteService.modificarCliente(cliente);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        try {
            clienteService.bajaCliente(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
