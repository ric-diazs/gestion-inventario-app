package com.example.gestion_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_inventario.model.Color;
import com.example.gestion_inventario.service.ColorService;

@RestController
@RequestMapping("/api/v1/colores")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> listarColores() {
        List<Color> colores = colorService.listarColores();

        if(colores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(colores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> buscarColorPorId(@PathVariable Integer id){
        try {
            Color color = colorService.buscarColorPorId(id);

            return ResponseEntity.ok(color);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Color> crearColor(@RequestBody Color color) {
        Color nuevoColor = colorService.guardarColor(color);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoColor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> actualizarColor(@PathVariable Integer id, @RequestBody Color color) {
        try {
            Color colorActualizar = colorService.buscarColorPorId(id);

            colorActualizar.setId(id);
            colorActualizar.setColor(color.getColor());

            colorService.guardarColor(colorActualizar);

            return ResponseEntity.ok(color);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarColor(@PathVariable Integer id) {
        try {
            colorService.eliminarColor(id);
            
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
