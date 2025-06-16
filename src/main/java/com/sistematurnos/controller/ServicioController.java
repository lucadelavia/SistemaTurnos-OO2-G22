package com.sistematurnos.controller;

import com.sistematurnos.dtos.request.ServicioRequest;
import com.sistematurnos.dtos.response.ServicioResponse;
import com.sistematurnos.dtos.mapper.ServicioMapper;
import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.entity.Servicio;
import com.sistematurnos.service.IEspecialidadService;
import com.sistematurnos.service.IServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "Gestión de servicios")
public class ServicioController {

    @Autowired
    private IServicioService servicioService;

    @Autowired
    private IEspecialidadService especialidadService;

    @Operation(summary = "Listar todos los servicios")
    @GetMapping
    public List<ServicioResponse> listarServicios() {
        return servicioService.traerServicios()
                .stream()
                .map(ServicioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Obtener servicio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServicioResponse> obtenerPorId(@PathVariable int id) {
        try {
            Servicio servicio = servicioService.obtenerServicioPorId(id);
            return ResponseEntity.ok(ServicioMapper.toResponse(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Obtener servicio por nombre")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<ServicioResponse> obtenerPorNombre(@PathVariable String nombre) {
        try {
            Servicio servicio = servicioService.obtenerServicioPorNombre(nombre);
            return ResponseEntity.ok(ServicioMapper.toResponse(servicio));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Listar servicios por especialidad")
    @GetMapping("/especialidad/{idEspecialidad}")
    public ResponseEntity<List<ServicioResponse>> serviciosPorEspecialidad(@PathVariable int idEspecialidad) {
        List<Servicio> servicios = servicioService.buscarPorEspecialidad(idEspecialidad);
        List<ServicioResponse> responseList = servicios.stream()
                .map(ServicioMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseList);
    }

    @Operation(summary = "Crear un nuevo servicio")
    @PostMapping
    public ResponseEntity<ServicioResponse> crear(@RequestBody ServicioRequest request) {
        try {
            Especialidad especialidad = especialidadService.obtenerEspecialidadPorId(request.idEspecialidad());
            Servicio servicio = ServicioMapper.toEntity(request, especialidad);
            Servicio nuevo = servicioService.altaServicio(servicio);
            return ResponseEntity.ok(ServicioMapper.toResponse(nuevo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Modificar un servicio")
    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponse> modificar(@PathVariable int id, @RequestBody ServicioRequest request) {
        try {
            Especialidad especialidad = especialidadService.obtenerEspecialidadPorId(request.idEspecialidad());
            Servicio servicio = ServicioMapper.toEntity(request, especialidad);
            servicio.setId(id);
            Servicio actualizado = servicioService.modificarServicio(servicio);
            return ResponseEntity.ok(ServicioMapper.toResponse(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar (baja lógica) un servicio")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        try {
            servicioService.bajaServicio(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
