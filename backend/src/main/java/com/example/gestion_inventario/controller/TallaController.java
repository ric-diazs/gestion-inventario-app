package com.example.gestion_inventario.controller;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;


import com.example.gestion_inventario.service.TallaService;
import com.example.gestion_inventario.model.Talla;

@RestController
@RequestMapping("/api/talla")
@RequiredArgsConstructor
public class TallaController {
    
    
    private final TallaService tallaService;

    @GetMapping
    public List<Talla> listar() {
        return tallaService.listar();
    }

    @PostMapping
    public Talla crear(@RequestBody Talla talla) {
        return tallaService.crear(talla);
    }
}
