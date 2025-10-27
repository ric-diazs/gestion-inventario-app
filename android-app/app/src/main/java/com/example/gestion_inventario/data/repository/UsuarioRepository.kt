package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.UsuarioDao
import com.example.gestion_inventario.data.local.entity.UsuarioEntity

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
}
