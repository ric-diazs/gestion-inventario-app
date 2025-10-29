package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoScreen(navController: NavController, productoId: Long) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

// Repositorios
    val usuarioRepo = UsuarioRepository(db.usuarioDao())
    val productoRepo = ProductoRepository(db.productoDao())

// Factory y ViewModel
    val factory = AuthViewModelFactory(
        usuarioRepository = usuarioRepo,
        productoRepository = productoRepo
    )
    val viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

// Estado del usuario
    val producto by viewModel.productoSeleccionado.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = productoId) {
        viewModel.cargarProductoPorId(productoId)
    }


    var codigo by remember { mutableStateOf("") }
    var nombreProducto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }


    LaunchedEffect(producto) {
        producto?.let {
            codigo = it.codigo
            nombreProducto = it.nombreProducto
            descripcion = it.descripcion
            talla = it.talla
            color = it.color
            precio = it.precio
            cantidad = it.cantidad

        }
    }

    var expanded by remember { mutableStateOf(false) }
    val tiposUsuario = listOf("Admin", "Empleado")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Usuario") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally, // 👈 Centra los campos
            verticalArrangement = Arrangement.Center // 👈 Centra verticalmente
        ) {
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Codigo") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombreProducto,
                onValueChange = { nombreProducto = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))


            OutlinedTextField(
                value = talla,
                onValueChange = { talla = it },
                label = { Text("Talla") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))



            // 🔹 Botones centrados
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    producto?.let {
                        viewModel.actualizarProducto(
                            it.copy(
                                codigo = codigo,
                                nombreProducto = nombreProducto,
                                descripcion = descripcion,
                                talla = talla,
                                color = color,
                                precio = precio,
                                cantidad = cantidad
                            )
                        ){
                            navController.popBackStack() // Vuelve atrás
                        }
                    }
                }) {
                    Text("Guardar")
                }

                Button(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }
            }
        }
    }
}
