package com.example.gestion_inventario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import com.example.gestion_inventario.service.ColorService;
import com.example.gestion_inventario.model.Color;

@RestController
@RequestMapping("/api/color")
@RequiredArgsConstructor
public class ColorController {
      private final ColorService colorService;

    @GetMapping
    public List<Color> listar() {
        return colorService.listar();
    }

    @PostMapping
    public Color crear(@RequestBody Color color) {
        return colorService.crear(color);
    }
}
