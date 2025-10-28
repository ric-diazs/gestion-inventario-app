package com.example.gestion_inventario.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gestion_inventario.data.local.entity.ReporteEntity

@Dao
interface ReporteDao {

    // Inserta un nuevo reporte (si hay conflicto de ID, aborta)
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(reporte: ReporteEntity): Long

    // Busca un reporte por email (por ejemplo, si quieres evitar duplicados)
    @Query("SELECT * FROM reporte WHERE emailrep = :emailrep LIMIT 1")
    suspend fun getByEmail(emailrep: String): ReporteEntity?

    // Obtiene todos los reportes
    @Query("SELECT * FROM reporte ORDER BY id ASC")
    suspend fun getAll(): List<ReporteEntity>

    // Elimina un reporte por su ID
    @Query("DELETE FROM reporte WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}