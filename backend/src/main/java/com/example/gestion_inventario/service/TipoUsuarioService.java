package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.TipoUsuario;
import com.example.gestion_inventario.repository.TipoUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoUsuarioService {
    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    // Regla de negocio:
    // Se enviaran al endpoint solo metodos para mostrar los tipos de usuarios
    public List<TipoUsuario> obtenerTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario obtenerUnTipoUsuario(Integer id) {
        return tipoUsuarioRepository.findById(id).get();
    }

}
