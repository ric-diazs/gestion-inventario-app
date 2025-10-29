package com.example.gestion_inventario.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

import com.example.gestion_inventario.data.local.entity.ProductoEntity

@Dao
interface ProductoDao {
    // Upsert -> Update + Insert:
    // Si el id que se intenta insertar existe, se aplica Update. Si no existe, se aplica Insert.
    @Upsert
    suspend fun upsertProducto(producto: ProductoEntity): Long

    @Delete
    suspend fun deleteProducto(producto: ProductoEntity)

    // Se obtiene un producto por su codigo
    @Query("SELECT * FROM producto WHERE codigo = :codigo")
    suspend fun obtenerProductoPorCodigo(codigo: String): ProductoEntity?

    @Query("SELECT * FROM producto ORDER BY id ASC")
    fun obtenerProductos(): Flow<List<ProductoEntity>>

    // Se obtiene la cantidad total de productos
    @Query("SELECT COUNT(*) FROM producto")
    suspend fun contarProductos(): Int
}