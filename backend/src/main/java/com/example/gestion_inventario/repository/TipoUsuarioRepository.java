package com.example.gestion_inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_inventario.model.TipoUsuario;

@Repository
public interface TipoUsuarioRepository extends JpaRepository<TipoUsuario, Integer> {

}
