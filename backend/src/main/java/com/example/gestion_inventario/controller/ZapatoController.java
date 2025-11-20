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

import com.example.gestion_inventario.model.Zapato;
import com.example.gestion_inventario.service.ZapatoService;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/zapatos")
public class ZapatoController {
    @Autowired
    private ZapatoService zapatoService;

    @GetMapping
    public ResponseEntity<List<Zapato>> listarZapatos() {
        List<Zapato> zapatos = zapatoService.listarZapatos();

        if(zapatos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(zapatos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Zapato> buscarZapatoPorId(@PathVariable Integer id) {
        try {
            Zapato zapato = zapatoService.buscarZapatoPorId(id);

            return ResponseEntity.ok(zapato);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Zapato> crearZapato(@RequestBody Zapato zapato) {
        Zapato nuevoZapato = zapatoService.guardarZapato(zapato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoZapato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zapato> actualizarZapato(@PathVariable Integer id, @RequestBody Zapato zapato) {
        try {
            Zapato zapatoActualizar = zapatoService.buscarZapatoPorId(id);

            zapatoActualizar.setId(id);
            zapatoActualizar.setNombre(zapato.getNombre());
            zapatoActualizar.setDescripcion(zapato.getDescripcion());
            zapatoActualizar.setTalla(zapato.getTalla());
            zapatoActualizar.setColor(zapato.getColor());
            zapatoActualizar.setPrecio(zapato.getPrecio());
            zapatoActualizar.setCantidad(zapato.getCantidad());

            zapatoService.guardarZapato(zapatoActualizar);

            return ResponseEntity.ok(zapato);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarZapato(@PathVariable Integer id) {
        try {
            zapatoService.eliminarZapato(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
