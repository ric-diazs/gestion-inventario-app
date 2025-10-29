package com.example.gestion_inventario.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import com.example.gestion_inventario.data.local.dao.UsuarioDao
import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import com.example.gestion_inventario.data.local.entity.ProductoEntity

// Definicion inicial basado en:
// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room#6
@Database(
	entities = [UsuarioEntity::class, ProductoEntity::class],
	version = 1,
	exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
	// Se inyecta el dao
	abstract fun usuarioDao(): UsuarioDao

	abstract fun productoDao(): ProductoDao

	// Para acceder a los metodos que permiten crear (create) y obtener (get) la base de datos
	// Tambien usa el nombre de la clase como cualificador (qualifier)
	companion object {
		@Volatile
		private var INSTANCE: AppDatabase? = null // Singleton que permitira tener una unica instancia abierta de la BBDD
		private const val DB_NAME = "gestion_inventario.db" // Nombre de la BBDD

		// Metodo para obtener la BBDD
		fun getDatabase(context: Context): AppDatabase {
			// Synchronized -> Controla que solo un hilo a la vez entre al codigo de la instancia de la BBDD
			//              -> Esto permite que la BBDD se inicialice solo una vez por ejecucion
			// Ademas, se define que si INSTANCE != null, regresa la BBDD. En cambio, si INSTANCE == null, se crea la BBDD
			return INSTANCE ?: synchronized(this) {
				// Uso de builder para obtener la BBDD (si no existe, se crea; si existe, se obtiene)
				val instance = Room.databaseBuilder(
					context.applicationContext,
					AppDatabase::class.java,
					DB_NAME
				)
					// Metodo callback para prepoblar la base de datos con un usuario al crearla
					.addCallback(object: RoomDatabase.Callback() {
						override fun onCreate(db: SupportSQLiteDatabase) {
							super.onCreate(db)

							// Insercion inicial de datos se hace mediante una corrutina
							CoroutineScope(Dispatchers.IO).launch {
								// Se llama al DAO de la entidad UsuarioEntity para usar metodo que
								// entrega la cantidad de usuarios que en la tabla 'usuario'
								val dao = getDatabase(context).usuarioDao()
								var cantUsuarios = dao.contarUsuarios()

								// Prepoblamiento de datos: Se inserta un usuario 'administrador'
								val registroInicial = listOf(
									UsuarioEntity(
										nombre = "John",
										apellidos = "Doe",
										email = "j.doe@admin.com",
										password = "*admin_12345Joe",
										tipoUsuario = "Admin"
									)
								)

								// Se inserta este registro inicial solo si la tabla 'usuario' esta vacia
								if(cantUsuarios == 0) {
									registroInicial.forEach{ dao.upsertUsuario(it) }
								}
							}
						}
					})
					.fallbackToDestructiveMigration() // Estrategia de migracion (en este caso se destruye y recrea, pero se usa cuando cambia el esquema y deben moverse los datos sin perderlos)
					.build()

				INSTANCE = instance

				instance
			}
		}
	}
}