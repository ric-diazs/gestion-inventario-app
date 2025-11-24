package com.example.gestion_inventario.data.remote.model

data class ProductoAPI(
	val id: Int,
	val nombre: String,
	val descripcion: String,
	val precio: Int,
	val cantidad: Int,
	val fechaRegistro: String
)