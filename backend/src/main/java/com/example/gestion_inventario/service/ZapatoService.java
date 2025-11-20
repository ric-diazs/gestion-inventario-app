package com.example.gestion_inventario.service;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestion_inventario.model.Zapato;
import com.example.gestion_inventario.repository.ZapatoRepository;

@Service
@Transactional
public class ZapatoService {
    @Autowired
    private ZapatoRepository zapatoRepository;

    public List<Zapato> listarZapatos() {
        return zapatoRepository.findAll();
    }

    public Zapato guardarZapato(Zapato zapato) {
        return zapatoRepository.save(zapato);
    }

    public Zapato buscarZapatoPorId(Integer id) {
        return zapatoRepository.findById(id).get();
    }

    public void eliminarZapato(Integer id) {
        zapatoRepository.deleteById(id);
    }
}
