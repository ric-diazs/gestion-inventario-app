package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import com.example.gestion_inventario.data.remote.ApiService
import com.example.gestion_inventario.data.remote.model.ColorAPI
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.ProductoAPI
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.TallaAPI
import com.example.gestion_inventario.data.remote.RetrofitInstance
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

    // Consultas API (Metodo GET)
    suspend fun obtenerProductosAPI(): List<ProductoAPI> = RetrofitInstance.api.obtenerProductos()

    suspend fun obtenerColoresAPI(): List<ColorAPI> = RetrofitInstance.api.obtenerColores()

    suspend fun obtenerTallasAPI(): List<TallaAPI> = RetrofitInstance.api.obtenerTallas()

    suspend fun obtenerProductoAPIPorId(id: Int): ProductoAPI = RetrofitInstance.api.obtenerProductoPorId(id)

    // Registro de productos en API (Metodo POST)
    suspend fun registrarProductoAPI(
        nombre: String,
        descripcion: String,
        idTalla: Int,
        idColor: Int,
        precio: Int,
        cantidad: Int
    ) {
        val bodyProducto = ProductoSolicitud(
            nombre = nombre,
            descripcion = descripcion,
            talla = IdObject(idTalla),
            color = IdObject(idColor),
            precio = precio,
            cantidad = cantidad
        )

        RetrofitInstance.api.registrarProducto(bodyProducto)
    }

    // Actualizar un producto en la API (Metodo PUT)
    suspend fun actualizarProductoAPI(id: Int, productoActualizar: ProductoSolicitud) {
        RetrofitInstance.api.actualizarProducto(id, productoActualizar)
    }

    // Eliminar un producto de la API (Metodo DELETE)
    suspend fun eliminarProductoAPI(id: Int) {
        RetrofitInstance.api.eliminarProducto(id)
    }
}
