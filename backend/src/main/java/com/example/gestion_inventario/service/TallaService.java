package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.Talla;
import com.example.gestion_inventario.repository.TallaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TallaService {
    @Autowired
    private TallaRepository tallaRepository;

    public List<Talla> listarTallas() {
        return tallaRepository.findAll();
    }

    public Talla buscarTallaPorId(Integer id) {
        return tallaRepository.findById(id).get();
    }

    public Talla guardarTalla(Talla talla) {
        return tallaRepository.save(talla);
    }

    public void eliminarTalla(Integer id) {
        tallaRepository.deleteById(id);
    }
}
