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
@Table(name = "nivel_prioridad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelPrioridad {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nivelPrioridad;

    // Relacion 1 a muchos a tabla 'reporte_problema'
    @OneToMany(mappedBy = "nivelPrioridad")
    @JsonManagedReference(value = "ref-reporte-problema-nivel-prioridad")
    private List<ReporteProblema> reportesProblema;
}
