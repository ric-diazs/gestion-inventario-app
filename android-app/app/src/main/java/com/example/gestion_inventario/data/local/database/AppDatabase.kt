package com.example.gestion_inventario.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.local.dao.RegistroDao
import com.example.gestion_inventario.data.local.dao.ReporteDao
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import com.example.gestion_inventario.data.local.entity.RegistroEntity
import com.example.gestion_inventario.data.local.entity.ReporteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [ProductoEntity::class, ReporteEntity::class, RegistroEntity::class],
    version = 1,
    exportSchema = true // Mantener true para inspección de esquema (útil en educación)
)
abstract class AppDatabase : RoomDatabase() {

    // Exponemos el DAO de usuarios

    abstract fun productoDao(): ProductoDao
    abstract fun registroDao(): RegistroDao

    abstract fun reporteDao(): ReporteDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null            // Instancia singleton
        private const val DB_NAME = "gestion_inventario.db"    // Nombre del archivo .db

        // Obtiene la instancia única de la base
        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                // Construimos la DB con callback de precarga

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DB_NAME
                )
                    // Callback para ejecutar cuando la DB se crea por primera vez
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            // Lanzamos una corrutina en IO para insertar datos iniciales
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = getDatabase(context).productoDao()

                                // Precarga de usuarios (administrador y empleado)
                                val seed = listOf(
                                    ProductoEntity(

                                        codigo = "",
                                        nombrepro = "",
                                        descripcion = "",
                                        talla = "",
                                        color = "",
                                        precio = "",
                                        cantidad = ""
                                    ),
                                    ProductoEntity(

                                        codigo = "",
                                        nombrepro = "",
                                        descripcion = "",
                                        talla = "",
                                        color = "",
                                        precio = "",
                                        cantidad = ""
                                    )
                                )

                            }
                        }
                    })
                    // En entorno educativo, si cambias versión sin migraciones, destruye y recrea.
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance                             // Guarda la instancia
                instance                                        // Devuelve la instancia
            }
        }
    }
}