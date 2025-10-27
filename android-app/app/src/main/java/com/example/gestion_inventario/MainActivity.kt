package com.example.gestion_inventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gestion_inventario.ui.theme.Gestion_inventarioTheme

import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.navigation.AppNavigation
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Lineas de codigo para construir la base de datos
            val context = LocalContext.current.applicationContext // Con 'applicationContext' se construye la BBDD

            val bbdd = AppDatabase.getDatabase(context) // Se obtiene solo una instancia (singleton) de la BBDD

            val usuarioDao = bbdd.usuarioDao()

            val usuarioRepository = UsuarioRepository(usuarioDao)

            // Ahora usamos ViewModel con Factory (mas info aca: https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories)
            val authViewModel: AuthViewModel = viewModel(
                factory = AuthViewModelFactory(usuarioRepository)
            )

            Gestion_inventarioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Se implementa flujo de navegacion en vez de mostrar una vista inicial
                    AppNavigation(authViewModel)
                }
            }
        }
    }
}