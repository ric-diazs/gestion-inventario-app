package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.ui.components.MainTopBar
import com.example.gestion_inventario.validation.validateContra
import com.example.gestion_inventario.validation.validateEmail
import com.example.gestion_inventario.validation.validateLastName
import com.example.gestion_inventario.validation.validateName2
import com.example.gestion_inventario.validation.validateNameUs


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RegistroUsuarioAdminScreen(navController: NavController){

    // Estados de los campos
    var nombreus by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("") }

    // Estados de errores
    var errorNombreUs by remember { mutableStateOf<String?>(null) }
    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorApellido by remember { mutableStateOf<String?>(null) }
    var errorEmail by remember { mutableStateOf<String?>(null) }
    var errorContrasena by remember { mutableStateOf<String?>(null) }
    var errorConfirmar by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {MainTopBar(navController)}
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Registrar Usuario",
                modifier = Modifier.padding(24.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge

            )
            // Nombre de usuario
            OutlinedTextField(
                value = nombreus,
                onValueChange = { nombreus = it },
                label = { Text("Nombre de usuario") },
                isError = errorNombreUs != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorNombreUs != null)
                Text(text = errorNombreUs!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                isError = errorNombre != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorNombre != null)
                Text(text = errorNombre!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Apellido
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") },
                isError = errorApellido != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorApellido!= null)
                Text(text = errorApellido!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorEmail = validateEmail(it) // Se valida al escribir
                },
                label = { Text("Correo electrónico") },
                isError = errorEmail != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Mensaje de error
            if (errorEmail != null) {
                Text(
                    text = errorEmail!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            // Contraseña
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("contraseña") },
                isError = errorContrasena != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorContrasena != null)
                Text(text = errorContrasena!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Confirmar Contraseña

            OutlinedTextField(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = { Text("Confirmar contraseña") },
                isError = errorConfirmar != null,
                modifier = Modifier.fillMaxWidth()
            )

            if (errorConfirmar != null)
                Text(
                    text = errorConfirmar!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )

            Spacer(modifier = Modifier.height(24.dp))

            // Tipo de usuario
            var expanded by remember { mutableStateOf(false) }
            val opciones = listOf("Administrador", "Empleado")
            var tipoUsuario by remember { mutableStateOf("") }

            Text(
                text = "Tipo de usuario:",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 4.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = tipoUsuario,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona tipo de usuario") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                tipoUsuario = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Botones
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    // Validaciones
                    errorNombreUs = validateNameUs(nombreus)
                    errorNombre = validateName2(nombre)
                    errorApellido = validateLastName(apellido)
                    errorEmail= validateEmail(email)
                    errorContrasena = validateContra(contrasena)

                    // Nueva validación: confirmar contraseña
                    errorConfirmar = if (confirmarContrasena != contrasena) {
                        "Las contraseñas no coinciden"
                    } else null

                    val hayError = listOf<String?>(
                        errorNombreUs,
                        errorNombre,
                        errorApellido,
                        errorEmail,
                        errorContrasena,
                        errorConfirmar
                    ).any { it != null }

                    if (!hayError) {
                        navController.popBackStack(Routes.RegistroUsuarioAdmin.ruta, inclusive = true)
                        navController.navigate(Routes.UsuariosAdmin.ruta)
                    }

                }) {
                    Text("Registrar")
                }

                OutlinedButton(onClick = {
                    navController.popBackStack() // Vuelve atrás
                }) {
                    Text("Cancelar")
                }
            }
        }
    }
}