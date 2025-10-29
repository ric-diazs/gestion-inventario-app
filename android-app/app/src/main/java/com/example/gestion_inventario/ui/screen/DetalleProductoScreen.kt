package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
fun DetalleProductoScreen(
    navController: NavController,
    productoId: Long
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val usuarioRepo = UsuarioRepository(db.usuarioDao())
    val productoRepo = ProductoRepository(db.productoDao())

    val factory = AuthViewModelFactory(usuarioRepo, productoRepo)
    val viewModel: AuthViewModel = viewModel(factory = factory)

    val producto by viewModel.productoSeleccionado.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Se accede al valor actual del estado del usuario logueado exitosamente a traves del ViewModel
    val estadoUsuario = viewModel.usuarioLogueado.collectAsState().value

    // Se accede al tipo de usuario logueado para hacer restricciones a vistas si no es 'Admin'
    val tipoUsuario: String = estadoUsuario?.tipoUsuario ?: ""

    // Cargar usuario al entrar
    LaunchedEffect(productoId) {
        viewModel.cargarProductoPorId(productoId)
    }

    MainDrawer(navController, drawerState, scope) {
        Scaffold(
            //topBar = { MainTopBar(navController, drawerState, scope) }
            topBar = {SecundaryTopBar(Routes.ProductosAdmin, navController)}
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                producto?.let { producto ->


                    Text(
                        text = "${producto.codigo} ${producto.nombreProducto}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Descripcion: ${producto.descripcion}")
                    Text(text = "Talla: ${producto.talla}")
                    Text(text = "Color: ${producto.color}")
                    Text(text = "Precio: ${producto.precio}")
                    Text(text = "Cantidad: ${producto.cantidad}")

                    Spacer(modifier = Modifier.height(24.dp))

                    // Se restringe a que solo un usuario de tipo "Admin" pueda editar o eliminar productos
                    if(tipoUsuario == "Admin"){
                        // 🔹 Botón para editar
                        Button(
                            onClick = {
                                navController.navigate("editarProducto/${producto.id}")
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Editar Producto")
                        }
    
                        Spacer(modifier = Modifier.height(8.dp))
    
                        // 🔹 Botón para eliminar
                        Button(
                            onClick = {
                                scope.launch {
                                    viewModel.eliminarProducto(producto.id)
                                    navController.popBackStack()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(text = "Eliminar Producto")
                        }
                    }
                } ?: Text(text = "Cargando producto...")
            }
        }
    }
}
