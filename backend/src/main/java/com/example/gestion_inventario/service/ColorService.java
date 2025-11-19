package com.example.gestion_inventario.service;

import com.example.gestion_inventario.model.Color;
import com.example.gestion_inventario.repository.ColorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorService {
    
    private final ColorRepository colorRepository;

    public List<Color> listar() {
        return colorRepository.findAll();
    }

    public Color crear(Color color) {
        return colorRepository.save(color);
    }
}
