package com.example.gestion_inventario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "producto")
data class ProductoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val codigo: String,
    val nombreProducto: String,
    val descripcion: String,
    val talla: String,
    val color: String,
    val precio: String,
    val cantidad: String
)