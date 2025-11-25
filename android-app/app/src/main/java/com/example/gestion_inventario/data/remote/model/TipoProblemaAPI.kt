package com.example.gestion_inventario.data.remote.model

data class TipoProblemaAPI(
    val id: Int,
    val tipoProblema: String,
    val reportesProblema: List<ReporteProblemaAPI>
)
