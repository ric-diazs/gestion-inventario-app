package com.example.gestion_inventario.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.gestion_inventario.data.local.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RegistroDao {
    // Upsert -> Update + Insert:
    // Si el id que se intenta insertar existe, se aplica Update. Si no existe, se aplica Insert.
    @Insert
    suspend fun insert(registro: RegistroEntity): Long

    @Delete
    suspend fun deleteUsuario(registro: RegistroEntity)

    // Se obtiene un usuario segun su email -> Se usa para validar login
    @Query("SELECT * FROM registro WHERE email = :email")
    suspend fun obtenerRegistro(email: String): RegistroEntity?

    // Para una lista de todos los usuarios con sus atributos
    // Se regresa una lista de usuarios, pero dentro de un flujo (flow)
    // Uso de flow aca: https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#5
    // Al usar 'Flow' no es necesario definir explicitamente a la funcion como 'suspend'
    @Query("SELECT * FROM registro ORDER BY id ASC")
    fun obtenerRegistros(): Flow<List<RegistroEntity>>

    // Se obtiene la cantidad total de usuarios
    @Query("SELECT COUNT(*) FROM registro")
    suspend fun contarRegistros(): Int
}