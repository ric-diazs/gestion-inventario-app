package com.example.gestion_inventario.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

import com.example.gestion_inventario.R
import com.example.gestion_inventario.navigation.Routes
//import com.example.gestion_inventario.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
	navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope//,
    //viewModel: AuthViewModel
) {
    // Se accede al valor actual del estado del usuario logueado exitosamente a traves del ViewModel
    //val estadoUsuario = viewModel.usuarioLogueado.collectAsState().value

    // Se accede al tipo de usuario logueado para hacer restricciones a vistas si no es 'Admin'
    //val tipoUsuario: String = estadoUsuario?.tipoUsuario ?: ""

	// Barra de navegacion (topbar): Es un TopBar centrado
	CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Inventario App",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1, // El texto sera solo de una linea
                overflow = TextOverflow.Ellipsis // Si el texto no cabe completamente, se agrega una elipsis (i.e, un '...')
            )
        },
        // Agregar accion para abrir el Menu Drawer
        navigationIcon = {
            IconButton(onClick = {scope.launch { drawerState.open() }}) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Icono de menu hamburguesa")
            }
        },
        actions = {
            IconButton(onClick = {navController.navigate(route = Routes.HomeAdmin.ruta)}) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "Icono de Home")
            }

            IconButton(onClick = {navController.navigate(route = Routes.ProductosAdmin.ruta)}) {
                Icon(painter = painterResource(id = R.drawable.ic_inventory), contentDescription = "Icono de Inventario de Productos", modifier = Modifier.size(24.dp))
            }

            // Restriccion: Solo usuario 'Admin' puede acceder a esta vista
            //if(tipoUsuario == "Admin") {
                IconButton(onClick = {navController.navigate(route = Routes.UsuariosAdmin.ruta)}) {
                    Icon(painter = painterResource(id = R.drawable.ic_groups), contentDescription = "Icono de Usuarios", modifier = Modifier.size(24.dp))
                }
            //}

            IconButton(onClick = {navController.navigate(route = Routes.ReportarProblema.ruta)}) {
                Icon(imageVector = Icons.Filled.Warning, contentDescription = "Icono de Reportar")
            }
        }
    )
}
