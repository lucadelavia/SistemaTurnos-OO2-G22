package com.sistematurnos.controller;

import com.sistematurnos.dtos.request.ClienteRequest;
import com.sistematurnos.dtos.response.ClienteResponse;
import com.sistematurnos.dtos.mapper.ClienteMapper;
import com.sistematurnos.entity.Cliente;
import com.sistematurnos.service.ClienteService;
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
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes activos")
    @GetMapping
    public List<ClienteResponse> listarClientes() {
        return clienteService.traerClientes().stream()
                .filter(Cliente::isEstado)
                .map(ClienteMapper::toResponse)
                .toList();
    }

    @Operation(summary = "Obtener un cliente por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> obtenerClientePorId(@PathVariable int id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        return ResponseEntity.ok(ClienteMapper.toResponse(cliente));
    }

    @Operation(summary = "Obtener un cliente por su número de cliente")
    @GetMapping("/nroCliente/{nro}")
    public ResponseEntity<ClienteResponse> obtenerPorNroCliente(@PathVariable int nro) {
        Cliente cliente = clienteService.traerClientePorNroCliente(nro);
        return ResponseEntity.ok(ClienteMapper.toResponse(cliente));
    }

    @Operation(summary = "Listar clientes con número mayor a un valor dado")
    @GetMapping("/mayorNroCliente/{limite}")
    public List<ClienteResponse> clientesConNroClienteMayorA(@PathVariable int limite) {
        return clienteService.findByNroClienteGreaterThan(limite).stream()
                .map(ClienteMapper::toResponse)
                .toList();
    }

    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping
    public ResponseEntity<ClienteResponse> crearCliente(@RequestBody ClienteRequest request) {
        Cliente nuevo = clienteService.altaCliente(ClienteMapper.toEntity(request));
        return ResponseEntity.ok(ClienteMapper.toResponse(nuevo));
    }

    @Operation(summary = "Modificar un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> modificarCliente(@PathVariable int id,
                                                            @RequestBody ClienteRequest request) {
        Cliente cliente = ClienteMapper.toEntity(request);
        cliente.setId(id);
        Cliente actualizado = clienteService.modificarCliente(cliente);
        return ResponseEntity.ok(ClienteMapper.toResponse(actualizado));
    }

    @Operation(summary = "Dar de baja (eliminar lógicamente) a un cliente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable int id) {
        clienteService.bajaCliente(id);
        return ResponseEntity.noContent().build();
    }
}
