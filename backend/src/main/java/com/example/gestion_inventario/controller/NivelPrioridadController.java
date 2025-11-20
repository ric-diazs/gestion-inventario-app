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

import com.example.gestion_inventario.model.NivelPrioridad;
import com.example.gestion_inventario.service.NivelPrioridadService;

@RestController
@RequestMapping("/api/v1/niveles-prioridad")
public class NivelPrioridadController {
    @Autowired
    private NivelPrioridadService nivelPrioridadService;

    @GetMapping
    public ResponseEntity<List<NivelPrioridad>> listarNivelesPrioridad() {
        List<NivelPrioridad> nivelesPrioridad = nivelPrioridadService.listarNivelesPrioridad();

        if(nivelesPrioridad.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(nivelesPrioridad);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NivelPrioridad> buscarNivelPrioridadPorId(@PathVariable Integer id) {
        try {
            NivelPrioridad nivelPrioridad = nivelPrioridadService.buscarNivelPrioridadPorId(id);

            return ResponseEntity.ok(nivelPrioridad);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NivelPrioridad> crearNivelPrioridad(@RequestBody NivelPrioridad nivelPrioridad) {
        NivelPrioridad nuevoNivelPrioridad = nivelPrioridadService.guardarNivelPrioridad(nivelPrioridad);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoNivelPrioridad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NivelPrioridad> actualizarNivelPrioridad(@PathVariable Integer id, @RequestBody NivelPrioridad nivelPrioridad) {
        try {
            NivelPrioridad nivelPrioridadActualizar = nivelPrioridadService.buscarNivelPrioridadPorId(id);

            nivelPrioridadActualizar.setId(id);
            nivelPrioridadActualizar.setNivelPrioridad(nivelPrioridad.getNivelPrioridad());

            nivelPrioridadService.guardarNivelPrioridad(nivelPrioridadActualizar);

            return ResponseEntity.ok(nivelPrioridad);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarNivelPrioridad(@PathVariable Integer id) {
        try {
            nivelPrioridadService.eliminarNivelPrioridad(id);
            
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
