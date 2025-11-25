package com.example.gestion_inventario.data.remote.model

data class TicketReporteAPI(
    val correo: String,
    val descripcion: String,
    val tipoProblema: IdObject,
    val nivelPrioridad: IdObject
)
