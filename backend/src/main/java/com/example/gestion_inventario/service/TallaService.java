package com.example.gestion_inventario.service;

import com.example.gestion_inventario.model.Talla;
import com.example.gestion_inventario.repository.TallaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallaService {
    
    private final TallaRepository tallaRepository;

    public List<Talla> listar() {
        return tallaRepository.findAll();
    }

    public Talla crear(Talla talla) {
        return tallaRepository.save(talla);
    }
}
