package com.example.gestion_inventario.data.remote.model

data class NivelPrioridadAPI(
    val id: Int,
    val nivelPrioridad: String,
    val reportesProblema: List<ReporteProblemaAPI>
)
