package com.example.gestion_inventario.data.remote.model

data class TallaAPI(
	val id: Int,
	val talla: Int,
	val zapatos: List<ProductoAPI>
)