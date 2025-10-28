package com.example.gestion_inventario.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "registro",
    indices = [Index(value = ["email"], unique = true)])
data class RegistroEntity(
    // Primary key autoincremental
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    val nombre: String,
    val apellido: String,
    val email: String,
    val contrasena: String,
    val confirmar: String,
    val tipoUsuario: String
)