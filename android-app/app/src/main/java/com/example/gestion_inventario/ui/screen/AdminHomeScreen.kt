package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.gestion_inventario.ui.components.MainDrawer
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

    val tipoUsuario: String = estadoUsuario?.tipoUsuario ?: ""

    var mensajeTipoUsuario: String = ""

    mensajeTipoUsuario = "Como ${tipoUsuario}"

    if(tipoUsuario == "Admin") {
        mensajeTipoUsuario = "$mensajeTipoUsuario tienes acceso a todas las funciones de la aplicación."
    } else {
        mensajeTipoUsuario = "$mensajeTipoUsuario solo puedes revisar los productos en inventario, ver tu perfil y reportar problemas."
    }

    // Variables para menu Drawer (Falta importar dependencias en header)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Implementacion de Drawer
    MainDrawer(navController, drawerState, scope){
        // Pagina principal (TopBar + contenido)
        Scaffold(
            //topBar = {MainTopBar(navController, drawerState, scope, viewModel)}
            topBar = {MainTopBar(navController, drawerState, scope)}
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
                //contentAlignment = Alignment.Center
            ){
                Text(
                    // Uso de operador Elvis si 'estadoUsuario == null' (en ese caso, no aparece algun nombre)
                    text = "Bienvenido ${estadoUsuario?.nombre ?: ""} a la App de Gestión de Inventarios",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            
            Spacer(modifier = Modifier.height(16.dp))

                Text(text = mensajeTipoUsuario)
            }
        }
    }
}
