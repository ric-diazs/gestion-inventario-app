package com.example.gestion_inventario.controller;

import com.example.gestion_inventario.model.Zapato;
import com.example.gestion_inventario.service.ZapatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zapatos")
@CrossOrigin(origins = "*")
public class ZapatoController {

    private final ZapatoService zapatoService;

    public ZapatoController(ZapatoService zapatoService) {
        this.zapatoService = zapatoService;
    }

    @GetMapping
    public List<Zapato> listar() {
        return zapatoService.listar();
    }

    @GetMapping("/{id}")
    public Zapato obtenerPorId(@PathVariable Integer id) {
        return zapatoService.buscarPorId(id);
    }

    @PostMapping
    public Zapato crear(@RequestBody Zapato zapato) {
        return zapatoService.guardar(zapato);
    }

    @PutMapping("/{id}")
    public Zapato actualizar(@PathVariable Integer id, @RequestBody Zapato zapato) {
        zapato.setId(id);
        return zapatoService.guardar(zapato);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        zapatoService.eliminar(id);
    }
}
