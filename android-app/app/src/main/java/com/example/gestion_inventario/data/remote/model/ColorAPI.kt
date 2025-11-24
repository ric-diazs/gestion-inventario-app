package com.example.gestion_inventario.data.remote.model

data class ColorAPI(
	val id: Int,
	val color: String,
	val zapatos: List<ProductoAPI>
)