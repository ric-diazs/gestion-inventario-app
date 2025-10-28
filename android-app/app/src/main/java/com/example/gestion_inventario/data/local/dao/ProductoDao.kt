package com.example.gestion_inventario.data.local.dao

import android.R
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gestion_inventario.data.local.entity.ProductoEntity

@Dao
interface ProductoDao {

    @Insert(onConflict = OnConflictStrategy.Companion.ABORT)
    suspend fun insert(producto : ProductoEntity): Long

    @Query("SELECT * FROM producto WHERE codigo = :codigo LIMIT 1")
    suspend fun getByCodigo(codigo: String): ProductoEntity?

    @Query("SELECT * FROM producto ORDER BY id ASC")
    suspend fun getAll(): List<ProductoEntity>

    @Query("DELETE FROM producto WHERE codigo = :codigo")
    suspend fun eliminarPorCodigo(codigo: String)
}