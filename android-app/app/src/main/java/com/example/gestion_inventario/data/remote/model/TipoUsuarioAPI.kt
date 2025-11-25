package com.example.gestion_inventario.data.remote.model

data class TipoUsuarioAPI(
	val id: Int,
	val nombreTipo: String,
	val usuarios: List<UsuarioAPI>
)