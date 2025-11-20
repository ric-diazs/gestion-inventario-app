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

    public List<TipoUsuario> obtenerTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario obtenerUnTipoUsuario(Integer id) {
        return tipoUsuarioRepository.findById(id).get();
    }
    
    public TipoUsuario guardarTipoUsuario(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void eliminarTipoUsuario(Integer id) {
        tipoUsuarioRepository.deleteById(id);
    }
}
