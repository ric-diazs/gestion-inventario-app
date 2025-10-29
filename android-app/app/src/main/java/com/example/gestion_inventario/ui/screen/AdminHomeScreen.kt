package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.gestion_inventario.ui.components.MainTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModel

//@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    // Se accede al valor actual del estado del usuario logueado exitosamente a traves del ViewModel
    val estadoUsuario = viewModel.usuarioLogueado.collectAsState().value

    // Pagina principal (TopBar + contenido)
    Scaffold(
        topBar = {MainTopBar(navController)}
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Text(
                // Uso de operador Elvis si 'estadoUsuario == null' (en ese caso, no aparece algun nombre)
                text = "Bienvenido ${estadoUsuario?.nombre ?: ""} a la App de Gestión de Inventarios",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
