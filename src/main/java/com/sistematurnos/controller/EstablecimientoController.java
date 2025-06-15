package com.sistematurnos.controller;

import com.sistematurnos.dtos.mapper.EstablecimientoMapper;
import com.sistematurnos.dtos.request.EstablecimientoRequest;
import com.sistematurnos.dtos.response.EstablecimientoResponse;
import com.sistematurnos.entity.Establecimiento;
import com.sistematurnos.service.IEstablecimientoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/establecimientos")
@Tag(name = "Establecimientos", description = "Gestión de establecimientos")
public class EstablecimientoController {

    @Autowired
    private IEstablecimientoService establecimientoService;

    @Operation(summary = "Listar todos los establecimientos")
    @GetMapping
    public List<EstablecimientoResponse> listarEstablecimientos() {
        return establecimientoService.traerEstablecimientos()
                .stream()
                .map(EstablecimientoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener establecimiento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EstablecimientoResponse> obtenerPorId(@PathVariable int id) {
        try {
            Establecimiento est = establecimientoService.traer(id);
            return ResponseEntity.ok(EstablecimientoMapper.toResponse(est));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo establecimiento")
    @PostMapping
    public ResponseEntity<EstablecimientoResponse> crear(@RequestBody EstablecimientoRequest request) {
        try {
            Establecimiento nuevo = establecimientoService.altaEstablecimiento(
                    EstablecimientoMapper.toEntity(request));
            return ResponseEntity.ok(EstablecimientoMapper.toResponse(nuevo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar un establecimiento")
    @PutMapping("/{id}")
    public ResponseEntity<EstablecimientoResponse> modificar(@PathVariable int id,
                                                             @RequestBody EstablecimientoRequest request) {
        try {
            Establecimiento actualizado = establecimientoService.modificarEstablecimiento(
                    EstablecimientoMapper.toEntity(id, request));
            return ResponseEntity.ok(EstablecimientoMapper.toResponse(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) un establecimiento")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            establecimientoService.bajaEstablecimiento(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
