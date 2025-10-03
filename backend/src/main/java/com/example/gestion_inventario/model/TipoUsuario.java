
package com.example.gestion_inventario.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoUsuario {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombreTipo;

    @OneToMany(mappedBy = "tipoUsuario")
    @JsonManagedReference
    private List<Usuario> usuarios;
}
