package com.example.gestion_inventario.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Zapato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 600)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_talla", nullable = false)
    private Talla talla;

    @ManyToOne
    @JoinColumn(name = "id_color", nullable = false)
    private Color color;

    @Column(nullable = false)
    private Integer precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @Column(length = 255)
    private String rutaImagen;
}
