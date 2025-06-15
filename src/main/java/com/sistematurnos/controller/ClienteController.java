package com.sistematurnos.controller;

import com.sistematurnos.entity.Cliente;
<<<<<<< HEAD
import com.sistematurnos.service.IClienteService;
=======
import com.sistematurnos.exception.ClienteNoEncontradoException;
import com.sistematurnos.service.ClienteService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
<<<<<<< HEAD
    private IClienteService clienteService;
=======
    private ClienteService clienteService;
>>>>>>> 99f4d3c (Version Funcional Spring Security)

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.traerClientes()
                .stream()
                .filter(Cliente::isEstado)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable int id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/nroCliente/{nro}")
    public ResponseEntity<Cliente> obtenerPorNroCliente(@PathVariable int nro) {
        Cliente cliente = clienteService.traerClientePorNroCliente(nro);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/mayorNroCliente/{limite}")
    public List<Cliente> clientesConNroClienteMayorA(@PathVariable int limite) {
        return clienteService.findByNroClienteGreaterThan(limite);
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.altaCliente(cliente);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable int id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        Cliente actualizado = clienteService.modificarCliente(cliente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        clienteService.bajaCliente(id);
        return ResponseEntity.noContent().build();
    }
}
