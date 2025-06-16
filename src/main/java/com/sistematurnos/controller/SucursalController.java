package com.sistematurnos.controller;

import com.sistematurnos.dtos.mapper.SucursalMapper;
import com.sistematurnos.dtos.response.SucursalResponse;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Sucursal;
import com.sistematurnos.service.IEspecialidadService;
import com.sistematurnos.service.ISucursalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Sucursales", description = "Gestión de sucursales y sus relaciones")
public class SucursalController {

    @Autowired
    private ISucursalService sucursalService;

    @Autowired
    private IEspecialidadService especialidadService;

    @Operation(summary = "Listar todas las sucursales")
    @GetMapping
    public List<SucursalResponse> listarSucursales() {
        return sucursalService.traerSucursales()
                .stream()
                .map(SucursalMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener sucursal por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SucursalResponse> obtenerPorId(@PathVariable int id) {
        try {
            Sucursal sucursal = sucursalService.traer(id);
            return ResponseEntity.ok(SucursalMapper.toResponse(sucursal));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva sucursal")
    @PostMapping
    public ResponseEntity<SucursalResponse> crear(@RequestBody Sucursal sucursal) {
        try {
            Sucursal nueva = sucursalService.altaSucursal(sucursal);
            return ResponseEntity.ok(SucursalMapper.toResponse(nueva));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Operation(summary = "Modificar una sucursal existente")
    @PutMapping("/{id}")
    public ResponseEntity<SucursalResponse> modificar(@PathVariable int id, @RequestBody Sucursal sucursal) {
        try {
            sucursal.setId(id);
            Sucursal modificada = sucursalService.modificarSucursal(sucursal);
            return ResponseEntity.ok(SucursalMapper.toResponse(modificada));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) una sucursal")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            sucursalService.bajaSucursal(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Asociar especialidad a una sucursal")
    @PostMapping("/{idSucursal}/especialidades")
    public ResponseEntity<Void> asociarEspecialidad(
            @PathVariable int idSucursal,
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad esp = especialidadService.obtenerEspecialidadPorId(especialidad.getId());
            sucursalService.asociarEspecialidad(idSucursal, esp);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Remover especialidad de una sucursal")
    @DeleteMapping("/{idSucursal}/especialidades")
    public ResponseEntity<Void> removerEspecialidad(
            @PathVariable int idSucursal,
            @RequestBody Especialidad especialidad) {
        try {
            Especialidad esp = especialidadService.obtenerEspecialidadPorId(especialidad.getId());
            sucursalService.removerEspecialidad(idSucursal, esp);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Asociar día de atención a una sucursal")
    @PostMapping("/{idSucursal}/dias-atencion/{idDia}")
    public ResponseEntity<Void> asociarDiaDeAtencion(
            @PathVariable int idSucursal,
            @PathVariable int idDia) {
        try {
            sucursalService.asociarDiaDeAtencion(idSucursal, idDia);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Remover día de atención de una sucursal")
    @DeleteMapping("/{idSucursal}/dias-atencion/{idDia}")
    public ResponseEntity<Void> removerDiaDeAtencion(
            @PathVariable int idSucursal,
            @PathVariable int idDia) {
        try {
            sucursalService.removerDiaDeAtencion(idSucursal, idDia);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
