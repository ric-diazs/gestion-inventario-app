package com.example.gestion_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestion_inventario.model.Zapato;

public interface ZapatoRepository extends JpaRepository<Zapato, Integer> {
}