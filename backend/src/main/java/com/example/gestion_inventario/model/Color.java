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
@Table(name = "color")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String color;

    @OneToMany(mappedBy = "color")
    @JsonManagedReference(value = "ref-zapato-color")
    private List<Zapato> zapatos;
}
