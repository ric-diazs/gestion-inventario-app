package com.example.gestion_inventario.data.remote.model

// BORRAR: ES SOLO PARA QUE FUNCIONE EL ENDPOINT DE REGISTRO DE USUARIO
data class ReporteProblemaAPI(
	val id: Int,
	val correo: String,
	val descripcion: String,
	val tipoProblema: TipoProblemaAPI,
	val nivelPrioridad: NivelPrioridadAPI
)