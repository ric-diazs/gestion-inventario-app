package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.UsuarioDao
import com.example.gestion_inventario.data.local.entity.UsuarioEntity
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


}





