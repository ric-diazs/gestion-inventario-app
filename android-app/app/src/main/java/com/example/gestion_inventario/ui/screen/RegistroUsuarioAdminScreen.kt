@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gestion_inventario.ui.viewmodel.RegistroAdminViewModel



// -------------------------------------------------------------
// 1️⃣ Pantalla conectada al ViewModel
// -------------------------------------------------------------
@Composable
fun RegistroUsuarioAdminScreenVm(
    vm: RegistroAdminViewModel,               // ViewModel específico de esta pantalla
    onSuccessNavigate: () -> Unit,          // Acción si el registro fue exitoso
    onCancel: () -> Unit                    // Acción al presionar "Cancelar"
) {
    val state by vm.registro.collectAsStateWithLifecycle()

    if (state.success) {                    // Si el registro fue exitoso
        vm.clearRegistroResult()            // Limpia el flag de éxito
        onSuccessNavigate()                 // Navega a la lista de usuarios, por ejemplo
    }

    // Delegamos la UI visual al composable presentacional
    RegistroUsuarioAdminScreen(
        nombre = state.nombre,
        apellido = state.apellido,
        email = state.email,
        contrasena = state.contrasena,
        confirmar = state.confirmar,
        tipoUsuario = state.tipoUsuario,


        errorNombre = state.errorNombre,
        errorApellido = state.errorApellido,
        errorEmail = state.errorEmail,
        errorContrasena = state.errorContrasena,
        errorConfirmar = state.errorConfirmar,

        isSubmitting = state.isSubmitting,
        errorMsg = state.errorMsg,

        onNombreChange = vm::onNombreChange,
        onApellidoChange = vm::onApellidoChange,
        onEmailChange = vm::onEmailChange,
        onContrasenaChange = vm::onContrasenaChange,
        onConfirmarChange = vm::onConfirmarChange,
        onTipoUsuarioChange = vm::onTipoUsuarioChange,
        onSubmit = vm::submitRegister,
        onCancel = onCancel
    )
}

// -------------------------------------------------------------
// 2️⃣ Pantalla de UI (solo presentación)
// -------------------------------------------------------------
@Composable
private fun RegistroUsuarioAdminScreen(
    nombre: String,
    apellido: String,
    email: String,
    contrasena: String,
    confirmar: String,
    tipoUsuario: String,


    errorNombre: String?,
    errorApellido: String?,
    errorEmail: String?,
    errorContrasena: String?,
    errorConfirmar: String?,
    isSubmitting: Boolean,
    errorMsg: String?,

    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onContrasenaChange: (String) -> Unit,
    onConfirmarChange: (String) -> Unit,
    onTipoUsuarioChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {
    val bg = MaterialTheme.colorScheme.background

    var showPass by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Registrar Usuario Administrador",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(12.dp))

            // ---------- NOMBRE ----------
            CampoTexto("Nombre", nombre, onNombreChange, errorNombre)

            // ---------- APELLIDO ----------
            CampoTexto("Apellido", apellido, onApellidoChange, errorApellido)

            // ---------- EMAIL ----------
            CampoTexto("Correo electrónico", email, onEmailChange, errorEmail)

            // ---------- CONTRASEÑA ----------
            OutlinedTextField(
                value = contrasena,
                onValueChange = onContrasenaChange,
                label = { Text("Contraseña") },
                singleLine = true,
                isError = errorContrasena != null,
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPass) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (errorContrasena != null) {
                Text(errorContrasena, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }

            Spacer(Modifier.height(8.dp))

            // ---------- CONFIRMAR CONTRASEÑA ----------
            OutlinedTextField(
                value = confirmar,
                onValueChange = onConfirmarChange,
                label = { Text("Confirmar contraseña") },
                singleLine = true,
                isError = errorConfirmar != null,
                visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { showConfirm = !showConfirm }) {
                        Icon(
                            imageVector = if (showConfirm) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showConfirm) "Ocultar confirmación" else "Mostrar confirmación"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (errorConfirmar != null) {
                Text(errorConfirmar, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }

            Spacer(Modifier.height(8.dp))

            // ---------- TIPO DE USUARIO ----------
            var expanded by remember { mutableStateOf(false) }
            val opciones = listOf("Administrador", "Empleado")

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = tipoUsuario,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Tipo de usuario") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onTipoUsuarioChange(opcion)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------- BOTÓN REGISTRAR ----------
            Button(
                onClick = onSubmit,
                enabled = !isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Creando usuario...")
                } else {
                    Text("Registrar")
                }
            }

            if (errorMsg != null) {
                Spacer(Modifier.height(8.dp))
                Text(errorMsg, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            // ---------- BOTÓN CANCELAR ----------
            OutlinedButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }
}

// -------------------------------------------------------------
// 3️⃣ CampoTexto reutilizable para no repetir tanto código
// -------------------------------------------------------------
@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    error: String?
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error != null,
        modifier = Modifier.fillMaxWidth()
    )
    if (error != null) {
        Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
    }
}
