package com.example.gestion_inventario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "producto")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)

    val id: Int = 0,

    val codigo: String,
    val nombrepro: String,
    val descripcion: String,
    val talla: String,
    val color: String,
    val precio: String,
    val cantidad: String
)
