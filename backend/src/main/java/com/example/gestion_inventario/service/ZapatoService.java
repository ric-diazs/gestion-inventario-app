package com.example.gestion_inventario.service;

import com.example.gestion_inventario.model.Zapato;
import com.example.gestion_inventario.repository.ZapatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZapatoService {

    private final ZapatoRepository zapatoRepository;

    public ZapatoService(ZapatoRepository zapatoRepository) {
        this.zapatoRepository = zapatoRepository;
    }

    public List<Zapato> listar() {
        return zapatoRepository.findAll();
    }

    public Zapato guardar(Zapato zapato) {
        return zapatoRepository.save(zapato);
    }

    public Zapato buscarPorId(Integer id) {
        return zapatoRepository.findById(id).orElse(null);
    }

    public void eliminar(Integer id) {
        zapatoRepository.deleteById(id);
    }
}
