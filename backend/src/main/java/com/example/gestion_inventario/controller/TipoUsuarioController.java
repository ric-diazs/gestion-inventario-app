package com.example.gestion_inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            return ResponseEntity.noContent().build()
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
}
