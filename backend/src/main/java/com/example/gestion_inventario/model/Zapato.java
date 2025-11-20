package com.example.gestion_inventario.model;

import java.time.LocalDate;

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
@Table(name = "zapato")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zapato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @ManyToOne
    @JsonBackReference(value = "ref-zapato-talla")
    @JoinColumn(name = "id_talla", nullable = false, foreignKey = @ForeignKey(name = "fk_talla"))
    private Talla talla;

    @ManyToOne
    @JsonBackReference(value = "ref-zapato-color")
    @JoinColumn(name = "id_color", nullable = false, foreignKey = @ForeignKey(name = "fk_color"))
    private Color color;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();
}
