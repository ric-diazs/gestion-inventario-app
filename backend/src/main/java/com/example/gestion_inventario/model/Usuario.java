
package com.example.gestion_inventario.model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String correo;

    @ManyToOne
    @JsonBackReference(value = "ref-usuario-tipo-usuario")
    @JoinColumn(name = "id_tipo_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_tipo_usuario"))
    private TipoUsuario tipoUsuario;

    @Column(nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    // Relacion uno-a-muchos a tabla 'reporte_problema'
    @OneToMany(mappedBy = "usuario")
    @JsonManagedReference(value = "ref-reporte-problema-usuario")
    private List<ReporteProblema> reportesProblema;
}