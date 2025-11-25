package com.example.gestion_inventario.data.remote.model

data class UsuarioSolicitud(
	val nombre: String,
	val apellidos: String,
	val correo: String,
	val password: String,
	val tipoUsuario: IdObject
)