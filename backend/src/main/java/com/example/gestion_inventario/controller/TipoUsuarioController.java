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

import com.example.gestion_inventario.model.TipoUsuario;
import com.example.gestion_inventario.service.TipoUsuarioService;

@RestController
@RequestMapping("/api/v1/tipos-usuario")
public class TipoUsuarioController {
    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @GetMapping
    public ResponseEntity<List<TipoUsuario>> obtenerTiposUsuario() {
        List<TipoUsuario> tiposUsuarios = tipoUsuarioService.obtenerTiposUsuario();

        if(tiposUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(tiposUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoUsuario> obtenerUnTipoUsuario(@PathVariable Integer id) {
        try {
            TipoUsuario tipoUsuario = tipoUsuarioService.obtenerUnTipoUsuario(id);

            return ResponseEntity.ok(tipoUsuario);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<TipoUsuario> crearTipoUsuario(@RequestBody TipoUsuario tipoUsuario) {
        TipoUsuario nuevoTipoUsuario = tipoUsuarioService.guardarTipoUsuario(tipoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoTipoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoUsuario> actualizarTipoUsuario(@PathVariable Integer id, @RequestBody TipoUsuario tipoUsuario) {
        try {
            TipoUsuario tipoUsuarioActualizar = tipoUsuarioService.obtenerUnTipoUsuario(id);

            tipoUsuarioActualizar.setId(id);
            tipoUsuarioActualizar.setNombreTipo(tipoUsuario.getNombreTipo());

            tipoUsuarioService.guardarTipoUsuario(tipoUsuarioActualizar);

            return ResponseEntity.ok(tipoUsuario);
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTipoUsuario(@PathVariable Integer id) {
        try {
            tipoUsuarioService.eliminarTipoUsuario(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
