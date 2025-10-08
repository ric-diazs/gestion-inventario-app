package com.example.gestion_inventario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.TipoProblema;
import com.example.gestion_inventario.repository.TipoProblemaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoProblemaService {
    
    @Autowired
    private TipoProblemaRepository tipoProblemaRepository;

    public List<TipoProblema> obtenerTipoProblemas() {
        return tipoProblemaRepository.findAll();
    }

    public TipoProblema obtenerUnTipoProblema(Integer id) {
        return tipoProblemaRepository.findById(id).get();
    }

    public TipoProblema guardarTipoProblema(TipoProblema tipoProblema) {
        return tipoProblemaRepository.save(tipoProblema);
    }

    public void eliminarTipoProblema(Integer id) {
        tipoProblemaRepository.deleteById(id);
    }
}
