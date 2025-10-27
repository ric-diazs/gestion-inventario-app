package com.example.gestion_inventario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class UsuarioEntity(
	// Primary key autoincremental
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0L,

	val nombre: String,
	val apellidos: String,
	val email: String,
	val password: String,
	val tipoUsuario: Long
)