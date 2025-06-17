package com.sistematurnos.service.implementation;

import com.sistematurnos.dtos.mapper.SucursalMapper;
import com.sistematurnos.dtos.request.SucursalRequest;
import com.sistematurnos.entity.*;
import com.sistematurnos.repository.IEspecialidadRepository;
import com.sistematurnos.repository.IEstablecimientoRepository;
import com.sistematurnos.repository.ISucursalRepository;
import com.sistematurnos.service.IDiasDeAtencionService;
import com.sistematurnos.service.ISucursalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SucursalService implements ISucursalService {

    @Autowired
    private ISucursalRepository sucursalRepository;

    @Autowired
    private IEspecialidadRepository especialidadRepository;

    @Autowired
    private IDiasDeAtencionService diasDeAtencionService;

    @Autowired
    private IEstablecimientoRepository establecimientoRepository;

    @Override
    public Sucursal altaSucursal(SucursalRequest dto) {
        if (sucursalRepository.findAll().stream()
                .anyMatch(s -> s.getDireccion().equalsIgnoreCase(dto.direccion()))) {
            throw new IllegalArgumentException("ERROR: ya existe una sucursal con la dirección: " + dto.direccion());
        }

        Sucursal sucursal = SucursalMapper.toEntity(dto);

        Establecimiento establecimiento = establecimientoRepository.findById(dto.idEstablecimiento())
                .orElseThrow(() -> new IllegalArgumentException("Establecimiento no encontrado con ID: " + dto.idEstablecimiento()));
        sucursal.setEstablecimiento(establecimiento);

        if (dto.idEspecialidades() != null && !dto.idEspecialidades().isEmpty()) {
            Set<Especialidad> especialidades = dto.idEspecialidades().stream()
                    .map(id -> especialidadRepository.findById(id)
                            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada: " + id)))
                    .collect(Collectors.toSet());
            sucursal.setLstEspecialidad(especialidades);
        }

        if (dto.idDiasDeAtencion() != null && !dto.idDiasDeAtencion().isEmpty()) {
            Set<DiasDeAtencion> dias = dto.idDiasDeAtencion().stream()
                    .map(diasDeAtencionService::traer)
                    .collect(Collectors.toSet());
            sucursal.setLstDiasDeAtencion(dias);
        }

        return sucursalRepository.save(sucursal);
    }

    @Override
    public void bajaSucursal(int id) {
        Sucursal suc = traer(id);
        suc.setEstado(false);
        sucursalRepository.save(suc);
    }

    @Override
    public Sucursal modificarSucursal(Sucursal suc) {
        Sucursal actual = traer(suc.getId());
        actual.setDireccion(suc.getDireccion());
        actual.setTelefono(suc.getTelefono());
        actual.setHoraApertura(suc.getHoraApertura());
        actual.setHoraCierre(suc.getHoraCierre());
        actual.setEspacio(suc.getEspacio());

        if (suc.getLstDiasDeAtencion() != null) {
            Set<DiasDeAtencion> dias = suc.getLstDiasDeAtencion().stream()
                    .map(d -> diasDeAtencionService.traer(d.getId()))
                    .collect(Collectors.toSet());
            actual.setLstDiasDeAtencion(dias);
        }

        if (suc.getLstEspecialidad() != null) {
            Set<Especialidad> especialidades = suc.getLstEspecialidad().stream()
                    .map(e -> especialidadRepository.findById(e.getId())
                            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada con ID: " + e.getId())))
                    .collect(Collectors.toSet());
            actual.setLstEspecialidad(especialidades);
        }

        return sucursalRepository.save(actual);
    }

    @Override
    public Sucursal modificarSucursal(int id, SucursalRequest dto) {
        Sucursal actual = traer(id);

        actual.setDireccion(dto.direccion());
        actual.setTelefono(dto.telefono());
        actual.setHoraApertura(dto.horaApertura());
        actual.setHoraCierre(dto.horaCierre());
        actual.setEspacio(dto.espacio());
        actual.setEstado(dto.estado());

        Establecimiento est = establecimientoRepository.findById(dto.idEstablecimiento())
                .orElseThrow(() -> new IllegalArgumentException("Establecimiento no encontrado"));
        actual.setEstablecimiento(est);

        if (dto.idEspecialidades() != null) {
            Set<Especialidad> especialidades = dto.idEspecialidades().stream()
                    .map(idEsp -> especialidadRepository.findById(idEsp)
                            .orElseThrow(() -> new IllegalArgumentException("Especialidad no encontrada: " + idEsp)))
                    .collect(Collectors.toSet());
            actual.setLstEspecialidad(especialidades);
        }

        if (dto.idDiasDeAtencion() != null) {
            Set<DiasDeAtencion> dias = dto.idDiasDeAtencion().stream()
                    .map(idDia -> diasDeAtencionService.traer(idDia))
                    .collect(Collectors.toSet());
            actual.setLstDiasDeAtencion(dias);
        }

        return sucursalRepository.save(actual);
    }

    @Override
    public Sucursal traer(int idSucursal) {
        return sucursalRepository.findById(idSucursal)
                .orElseThrow(() -> new IllegalArgumentException("ERROR: no existe una sucursal con ID: " + idSucursal));
    }

    @Override
    public List<Sucursal> traerSucursales() {
        return sucursalRepository.findAll().stream()
                .filter(Sucursal::isEstado)
                .toList();
    }

    @Override
    public void asociarDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (!suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().add(dia);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void removerDiaDeAtencion(int idSucursal, int idDiasAtencion) {
        Sucursal suc = traer(idSucursal);
        DiasDeAtencion dia = diasDeAtencionService.traer(idDiasAtencion);
        if (suc.getLstDiasDeAtencion().contains(dia)) {
            suc.getLstDiasDeAtencion().remove(dia);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void asociarEspecialidad(int idSucursal, Especialidad especialidad) {
        Sucursal suc = traer(idSucursal);
        if (!suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().add(especialidad);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public void removerEspecialidad(int idSucursal, Especialidad especialidad) {
        Sucursal suc = traer(idSucursal);
        if (suc.getLstEspecialidad().contains(especialidad)) {
            suc.getLstEspecialidad().remove(especialidad);
            sucursalRepository.save(suc);
        }
    }

    @Override
    public Sucursal guardar(Sucursal suc) {
        return sucursalRepository.save(suc);
    }
}
