package com.example.gestion_inventario.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey




    @Entity(tableName = "reporte")
    data class ReporteEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,

        val nombrerep: String,
        val emailrep: String,
        val descripcionrep: String
    )
