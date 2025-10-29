package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao){
    // Registro: Valida posible duplicamiento y registra el producto en caso de no haber duplicados
    suspend fun registrarProducto(
        codigo: String,
        nombreProducto: String,
        descripcion: String,
        talla: String,
        color: String,
        precio: String,
        cantidad: String
    ): Result<Long> {
        // ¿Hay un producto con el codigo que se esta ingresado?
        val existeProducto = productoDao.obtenerProductoPorCodigo(codigo) != null

        if (existeProducto) {
            return Result.failure(IllegalStateException("El codigo ya está registrado"))
        }

        val id = productoDao.upsertProducto(
            ProductoEntity(
                codigo = codigo,
                nombreProducto = nombreProducto,
                descripcion = descripcion,
                talla = talla,
                color = color,
                precio = precio,
                cantidad = cantidad
            )
        )
        return Result.success(id) // Devuelve ID autogenerado
    }

    fun obtenerProductos(): Flow<List<ProductoEntity>> = productoDao.obtenerProductos()

    suspend fun obtenerProductoPorId(id: Long): ProductoEntity? {
        return productoDao.obtenerProductoPorId(id)
    }

    suspend fun eliminarProducto(id: Long) {
        val producto = productoDao.obtenerProductoPorId(id)
        if (producto != null) {
            productoDao.deleteProducto(producto)
        }
    }

    suspend fun actualizarProducto(producto: ProductoEntity) {
        productoDao.upsertProducto(producto)
    }
}
