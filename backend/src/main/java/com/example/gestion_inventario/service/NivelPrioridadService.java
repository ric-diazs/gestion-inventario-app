package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.NivelPrioridad;
import com.example.gestion_inventario.repository.NivelPrioridadRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NivelPrioridadService {
    @Autowired
    private NivelPrioridadRepository nivelPrioridadRepository;

    public List<NivelPrioridad> listarNivelesPrioridad() {
        return nivelPrioridadRepository.findAll();
    }

    public NivelPrioridad buscarNivelPrioridadPorId(Integer id) {
        return nivelPrioridadRepository.findById(id).get();
    }

    public NivelPrioridad guardarNivelPrioridad(NivelPrioridad nivelPrioridad) {
        return nivelPrioridadRepository.save(nivelPrioridad);
    }

    public void eliminarNivelPrioridad(Integer id) {
        nivelPrioridadRepository.deleteById(id);
    }
}
