package com.example.gestion_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gestion_inventario.model.TipoProblema;
import com.example.gestion_inventario.service.TipoProblemaService;

@RestController
@RequestMapping("/api/v1/tipos-problema")
public class TipoProblemaController {
    @Autowired
    private TipoProblemaService tipoProblemaService;

    @GetMapping
    public ResponseEntity<List<TipoProblema>>obtenerTiposProblema() {
        List<TipoProblema> tiposProblema = tipoProblemaService.obtenerTipoProblemas;

        if(tiposProblema.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tiposProblema);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoProblema> obtenerUnTipoProblema(@PathVariable Integer id) {
        try {
            TipoProblema tipoProblema = tipoProblemaService.obtenerUnTipoProblema(id);

            return ResponseEntity.ok(tipoProblema);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoProblema> crearTipoProblema(@RequestBody TipoProblema tipoProblema) {
        TipoProblema nuevoTipoProblema = tipoProblemaService.guardarTipoProblema(tipoProblema);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipoProblema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoProblema> actualizarTipoProblema(@PathVariable Integer id, @RequestBody TipoProblema tipoProblema) {
        try {
            TipoProblema tipoProblemaActualizar = tipoProblemaService.obtenerUnTipoProblema(id);

            tipoProblemaActualizar.setId(id);
            tipoProblemaActualizar.setTipoProblema(tipoProblema.getTipoProblema());

            tipoProblemaService.guardarTipoProblema(tipoProblemaActualizar);

            return ResponseEntity.ok(tipoProblema);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> eliminarTipoProblema(@PathVariable Integer id) {
        try {
            tipoProblemaService.eliminarTipoProblema(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
