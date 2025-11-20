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

import com.example.gestion_inventario.model.Talla;
import com.example.gestion_inventario.service.TallaService;

@RestController
@RequestMapping("/api/v1/tallas")
public class TallaController {
    @Autowired
    private TallaService tallaService;

    @GetMapping
    public ResponseEntity<List<Talla>> listarTallas() {
        List<Talla> tallas = tallaService.listarTallas();

        if(tallas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tallas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> buscarTallaPorId(@PathVariable Integer id) {
        try {
            Talla talla = tallaService.buscarTallaPorId(id);

            return ResponseEntity.ok(talla);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Talla> crearTalla(@RequestBody Talla talla) {
        Talla nuevaTalla = tallaService.guardarTalla(talla);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaTalla);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talla> actualizarTalla(@PathVariable Integer id, @RequestBody Talla talla) {
        try {
            Talla tallaActualizar = tallaService.buscarTallaPorId(id);
            
            tallaActualizar.setId(id);
            tallaActualizar.setTalla(talla.getTalla());

            tallaService.guardarTalla(tallaActualizar);
            
            return ResponseEntity.ok(talla);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTalla(@PathVariable Integer id){
        try {
            tallaService.eliminarTalla(id);

            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
