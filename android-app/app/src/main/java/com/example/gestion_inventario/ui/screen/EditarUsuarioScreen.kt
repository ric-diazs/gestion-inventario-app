package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarUsuarioScreen(navController: NavController, usuarioId: Long) {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

// Repositorios
    val usuarioRepo = UsuarioRepository(db.usuarioDao())

// Factory y ViewModel
    val factory = AuthViewModelFactory(
        usuarioRepository = usuarioRepo
    )
    val viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

// Estado del usuario
    val usuario by viewModel.usuarioSeleccionado.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = usuarioId) {
        viewModel.cargarUsuarioPorId(usuarioId)
    }


    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var tipoUsuario by remember { mutableStateOf("") }

    LaunchedEffect(usuario) {
        usuario?.let {
            nombre = it.nombre
            apellidos = it.apellidos
            email = it.email
            tipoUsuario = it.tipoUsuario
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
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔽 Campo desplegable tipo usuario
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = tipoUsuario,
                    onValueChange = {},
                    label = { Text("Tipo de Usuario") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(0.9f)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tiposUsuario.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo) },
                            onClick = {
                                tipoUsuario = tipo
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 🔹 Botones centrados
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    usuario?.let {
                            viewModel.actualizarUsuario(
                                it.copy(
                                    nombre = nombre,
                                    apellidos = apellidos,
                                    email = email,
                                    tipoUsuario = tipoUsuario
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
