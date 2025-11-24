package com.example.gestion_inventario.data.remote.model

data class ProductoSolicitud(
	val nombre: String,
	val descripcion: String,
	val talla: IdObject,
	val color: IdObject,
	val precio: Int,
	val cantidad: Int
)