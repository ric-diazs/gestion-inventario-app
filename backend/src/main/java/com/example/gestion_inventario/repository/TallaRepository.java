package com.example.gestion_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestion_inventario.model.Talla;

public interface TallaRepository extends JpaRepository<Talla, Integer> {
}
