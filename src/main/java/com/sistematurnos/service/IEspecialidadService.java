package com.sistematurnos.service;

import com.sistematurnos.entity.Especialidad;
import com.sistematurnos.repository.IEspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IEspecialidadService {
    public List<Especialidad> traerEspecialidades();

    
    
}
