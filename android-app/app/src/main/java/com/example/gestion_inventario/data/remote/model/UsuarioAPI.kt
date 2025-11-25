package com.example.gestion_inventario.data.remote.model

data class UsuarioAPI(
	val id: Int,
	val nombre: String,
	val apellidos: String,
	val correo: String,
	val password: String,
	val fechaRegistro: String,
	val reportesProblema: List<ReporteProblemaAPI>
)