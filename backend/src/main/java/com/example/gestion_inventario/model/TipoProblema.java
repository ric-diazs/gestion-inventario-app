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
@Table(name = "tipo_problema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoProblema {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String tipoProblema;

    // Relacion 1 a muchos a tabla 'reporte_problema'
    @OneToMany(mappedBy = "tipoProblema")
    @JsonManagedReference(value = "ref-reporte-problema-tipo-problema")
    private List<ReporteProblema> reportesProblema;
}