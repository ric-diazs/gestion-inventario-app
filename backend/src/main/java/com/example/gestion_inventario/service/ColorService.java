package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.Color;
import com.example.gestion_inventario.repository.ColorRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ColorService {
    @Autowired
    private ColorRepository colorRepository;

    public List<Color> listarColores() {
        return colorRepository.findAll();
    }

    public Color buscarColorPorId(Integer id) {
        return colorRepository.findById(id).get();
    }

    public Color guardarColor(Color color) {
        return colorRepository.save(color);
    }

    public void eliminarColor(Integer id) {
        colorRepository.deleteById(id);
    }
}
