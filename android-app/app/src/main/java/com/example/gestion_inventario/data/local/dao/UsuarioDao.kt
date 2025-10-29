package com.example.gestion_inventario.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

import com.example.gestion_inventario.data.local.entity.UsuarioEntity

@Dao
interface UsuarioDao {
	// Upsert -> Update + Insert:
	// Si el id que se intenta insertar existe, se aplica Update. Si no existe, se aplica Insert.
	@Upsert
	suspend fun upsertUsuario(usuario: UsuarioEntity): Long

	@Delete
	suspend fun deleteUsuario(usuario: UsuarioEntity)

	// Se obtiene un usuario segun su email -> Se usa para validar login
	@Query("SELECT * FROM usuario WHERE email = :email")
	suspend fun obtenerUsuarioPorEmail(email: String): UsuarioEntity?

	// Para una lista de todos los usuarios con sus atributos
	// Se regresa una lista de usuarios, pero dentro de un flujo (flow)
	// Uso de flow aca: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#5
	// Al usar 'Flow' no es necesario definir explicitamente a la funcion como 'suspend'
	@Query("SELECT * FROM usuario ORDER BY id ASC")
	fun obtenerUsuarios(): Flow<List<UsuarioEntity>>

	// Se obtiene la cantidad total de usuarios
	@Query("SELECT COUNT(*) FROM usuario")
	suspend fun contarUsuarios(): Int
}