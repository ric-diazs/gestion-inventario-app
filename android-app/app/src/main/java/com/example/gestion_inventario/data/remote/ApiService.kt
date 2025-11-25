package com.example.gestion_inventario.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.POST
import retrofit2.http.PUT
import kotlinx.coroutines.flow.Flow

import com.example.gestion_inventario.data.remote.model.ColorAPI
import com.example.gestion_inventario.data.remote.model.ProductoAPI
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.TallaAPI
import com.example.gestion_inventario.data.remote.model.TipoUsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioSolicitud

interface ApiService {
	// Llamadas a endpoints de Producto de API
	@GET("zapatos")
	suspend fun obtenerProductos(): List<ProductoAPI>

	@GET("zapatos/{id}")
	suspend fun obtenerProductoPorId(@Path("id") id: Int): ProductoAPI

	@POST("zapatos")
	suspend fun registrarProducto(@Body producto: ProductoSolicitud)

	@PUT("zapatos/{id}")
	suspend fun actualizarProducto(@Path("id") id: Int, @Body producto: ProductoSolicitud)

	@DELETE("zapatos/{id}")
	suspend fun eliminarProducto(@Path("id") id: Int)

	@GET("colores")
	suspend fun obtenerColores(): List<ColorAPI>

	@GET("tallas")
	suspend fun obtenerTallas(): List<TallaAPI>

	// Llamadas a endpoints de Usuario
	@GET("tipos-usuario")
	suspend fun obtenerTiposUsuario(): List<TipoUsuarioAPI>

	@GET("usuarios")
	suspend fun obtenerUsuarios(): List<UsuarioAPI>

	@GET("usuarios/{id}")
	suspend fun obtenerUsuarioPorId(@Path("id") id: Int): UsuarioAPI

	@POST("usuarios")
	suspend fun registrarUsuario(@Body usuario : UsuarioSolicitud)

	@PUT("usuarios/{id}")
	suspend fun actualizarUsuario(@Path("id") id: Int, @Body usuario: UsuarioSolicitud)

	@DELETE("usuarios/{id}")
	suspend fun eliminarUsuario(@Path("id") id: Int)
}