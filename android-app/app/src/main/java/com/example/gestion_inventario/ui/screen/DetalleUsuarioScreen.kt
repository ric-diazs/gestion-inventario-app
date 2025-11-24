package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.ui.components.MainDrawer
//import com.example.gestion_inventario.ui.components.MainTopBar
import com.example.gestion_inventario.ui.components.SecundaryTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelFactory
import com.example.gestion_inventario.navigation.Routes
import kotlinx.coroutines.launch

@Composable
fun DetalleUsuarioScreen(
    navController: NavController,
    usuarioId: Long
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val usuarioRepo = UsuarioRepository(db.usuarioDao())

    val factory = AuthViewModelFactory(usuarioRepo)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    val usuario by viewModel.usuarioSeleccionado.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Cargar usuario al entrar
    LaunchedEffect(usuarioId) {
        viewModel.cargarUsuarioPorId(usuarioId)
    }

    MainDrawer(navController, drawerState, scope) {
        Scaffold(
            topBar = {SecundaryTopBar(Routes.UsuariosAdmin, navController)}
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                usuario?.let { usuario ->
                    Text(
                        text = "${usuario.nombre} ${usuario.apellidos}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Correo: ${usuario.email}")
                    Text(text = "Tipo: ${usuario.tipoUsuario}")

                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔹 Botón para editar
                    Button(
                        onClick = {
                            navController.navigate("editarUsuario/${usuario.id}")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Editar Usuario")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 🔹 Botón para eliminar
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.eliminarUsuario(usuario.id)
                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Eliminar Usuario")
                    }
                } ?: Text(text = "Cargando usuario...")
            }
        }
    }
}
