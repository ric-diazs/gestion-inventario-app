package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.UsuarioDao
import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.TipoUsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioSolicitud
import com.example.gestion_inventario.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.Flow

// Implementacion de logica de negocio mediante el uso de las funciones definidas en interfaz del DAO
class UsuarioRepository(private val usuarioDao: UsuarioDao) {
	// Validacion de credenciales correctas para login
	// 'Result' es una clase de la biblioteca standard de Kotlin usada, entre otras cosas,
	// para el manejo de errores: https://www.baeldung.com/kotlin/result-class


	suspend fun validarLogin(email: String, password: String): Result<UsuarioEntity> {
		// La funcion debe ser asincronica porque obtenerUsuarioPorEmail() tambien lo es
		val usuario = usuarioDao.obtenerUsuarioPorEmail(email)

		if(usuario != null && usuario.password == password) {
			return Result.success(usuario)
		} else {
			return Result.failure(IllegalArgumentException("Ingresó las credenciales incorrectas"))
		}
	}

	suspend fun registrarUsuario(
		nombre: String,
		apellido: String,
		email: String,
		password: String,
		tipoUsuario: String
	): Result<Long> {


		// Verificar si el correo ya existe
		val exists = usuarioDao.obtenerUsuarioPorEmail(email) != null
		if (exists) {
			return Result.failure(IllegalStateException("El correo ya está registrado"))
		}

		// Crear usuario nuevo
		val id = usuarioDao.upsertUsuario(
			UsuarioEntity(
				nombre = nombre,
				apellidos = apellido,
				email = email,
				password = password,
				tipoUsuario = tipoUsuario
			)
		)

		return Result.success(id)
	}
	fun obtenerUsuarios(): Flow<List<UsuarioEntity>> = usuarioDao.obtenerUsuarios()

	suspend fun obtenerUsuarioPorId(id: Long): UsuarioEntity? {
		return usuarioDao.obtenerUsuarioPorId(id)
	}

	suspend fun eliminarUsuario(id: Long) {
		val usuario = usuarioDao.obtenerUsuarioPorId(id)
		if (usuario != null) {
			usuarioDao.deleteUsuario(usuario)
		}
	}

	suspend fun actualizarUsuario(usuario: UsuarioEntity) {
		usuarioDao.upsertUsuario(usuario)
	}

	// Consultas API (Metodo GET)
	suspend fun obtenerTiposUsuarioAPI() : List<TipoUsuarioAPI> = RetrofitInstance.api.obtenerTiposUsuario()

	suspend fun obtenerUsuariosAPI() : List<UsuarioAPI> = RetrofitInstance.api.obtenerUsuarios()

	suspend fun obtenerUsuarioAPIPorId(id: Int) : UsuarioAPI = RetrofitInstance.api.obtenerUsuarioPorId(id)

	// Registro de usuarios en API (Metodo POST)
	suspend fun registrarUsuarioAPI(
		nombre: String,
		apellidos: String,
		correo: String,
		password: String,
		idTipoUsuario: Int
	){
		val bodyUsuario = UsuarioSolicitud(
			nombre = nombre,
			apellidos = apellidos,
			correo = correo,
			password = password,
			tipoUsuario = IdObject(idTipoUsuario)
		)

		RetrofitInstance.api.registrarUsuario(bodyUsuario)
	}

	// Actualizar usuario en API (Metodo PUT)
	suspend fun actualizarUsuarioAPI(id: Int, usuarioActualizar: UsuarioSolicitud) {
		return RetrofitInstance.api.actualizarUsuario(id, usuarioActualizar)
	}

	// Eliminar usuario en API (Metodo DELETE)
	suspend fun eliminarUsuarioAPI(id: Int) {
		return RetrofitInstance.api.eliminarUsuario(id)
	}
}





