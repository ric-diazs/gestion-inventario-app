package com.example.gestion_inventario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte_problema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProblema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = true)
    private String descripcion;

    // Relaciones
    // Relacion muchos-a-uno a tabla 'tipo_problema'
    @ManyToOne
    @JsonBackReference(value = "ref-reporte-problema-tipo-problema")
    @JoinColumn(name = "id_tipo_problema", nullable = false, foreignKey = @ForeignKey(name = "fk_tipo_problema"))
    private TipoProblema tipoProblema;

    // Relacion muchos-a-uno a tabla 'usuario'
    @ManyToOne
    @JsonBackReference(value = "ref-reporte-problema-usuario")
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name="fk_usuario"))
    private Usuario usuario;

    // Relacion muchos-a-uno a tabla 'nivel_prioridad'
    @ManyToOne
    @JsonBackReference(value = "ref-reporte-problema-nivel-prioridad")
    @JoinColumn(name = "id_nivel_prioridad", nullable = false, foreignKey = @ForeignKey(name = "fk_nivel_prioridad"))
    private NivelPrioridad nivelPrioridad;
}
