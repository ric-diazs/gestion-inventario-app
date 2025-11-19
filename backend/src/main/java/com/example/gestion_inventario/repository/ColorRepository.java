package com.example.gestion_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.gestion_inventario.model.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {
}
