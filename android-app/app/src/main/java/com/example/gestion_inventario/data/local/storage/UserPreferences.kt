package com.example.gestion_inventario.data.local.storage

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// ⚙️ Extensión del Contexto para crear un DataStore de tipo Preferences
val Context.userDataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences(private val context: Context) {

    // 🔑 Clave para guardar el estado de sesión
    private val isLoggedInKey = booleanPreferencesKey("is_logged_in")

    // 🧠 Guardar el estado de sesión (true/false)
    suspend fun setLoggedIn(value: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[isLoggedInKey] = value
        }
    }

    // 👀 Leer el estado de sesión (Flow reactivo)
    val isLoggedIn: Flow<Boolean> = context.userDataStore.data.map { preferences ->
        preferences[isLoggedInKey] ?: false
    }
}