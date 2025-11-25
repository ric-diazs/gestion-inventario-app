package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.UsuarioSolicitud
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarUsuarioScreen(
    navController: NavController,
    viewModel: AuthViewModel,
    usuarioId: Int
    //usuarioId: Long
) {
    //val context = LocalContext.current
    //val db = AppDatabase.getDatabase(context)

// Repositorios
    //val usuarioRepo = UsuarioRepository(db.usuarioDao())

// Factory y ViewModel
    //val factory = AuthViewModelFactory(
    //    usuarioRepository = usuarioRepo
    //)
    //val viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)

// Estado del usuario
    val usuario by viewModel.usuarioSeleccionado.collectAsState()

    val usuarioApi by viewModel.usuarioApi.collectAsState()

    val tiposUsuario by viewModel.tiposUsuario.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = usuarioId) {
        viewModel.cargarUsuarioApiPorId(usuarioId)
    }

    LaunchedEffect(Unit){
        viewModel.cargarTiposUsuarioApi()
    }

    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var idTipoUsuario by remember { mutableStateOf<Int?>(null) }
    //var tipoUsuario by remember { mutableStateOf("") }

    LaunchedEffect(usuarioApi) {
        usuarioApi?.let {
            nombre = it.nombre
            apellidos = it.apellidos
            password = it.password
            correo = it.correo
            idTipoUsuario = viewModel.obtenerTipoUsuarioDeUsuario(usuarioId)?.id
        }
    }

    var expanded by remember { mutableStateOf(false) }
    //val tiposUsuario = listOf("Admin", "Empleado")

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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = apellidos,
                onValueChange = { apellidos = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo desplegable tipo usuario
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = tiposUsuario.firstOrNull { it.id == idTipoUsuario }?.nombreTipo ?: "",
                    onValueChange = {},
                    label = { Text("Tipo de Usuario") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    tiposUsuario.forEach { tipoUser ->
                        DropdownMenuItem(
                            text = { Text(tipoUser.nombreTipo) },
                            onClick = {
                                //tipoUsuario = tipo
                                idTipoUsuario = tipoUser.id
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
                    val usuarioActualizar = UsuarioSolicitud(
                        nombre = nombre,
                        apellidos = apellidos,
                        correo = correo,
                        password = password,
                        tipoUsuario = IdObject(idTipoUsuario!!)
                    )

                    viewModel.submitActualizarUsuarioAPI(usuarioId, usuarioActualizar)
                    /*usuario?.let {
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
                    }*/
                }) {
                    Text("Actualizar")
                }

                Button(onClick = { navController.popBackStack() }) {
                    Text("Cancelar")
                }
            }
        }
    }
}
