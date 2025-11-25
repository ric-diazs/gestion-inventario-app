package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.ui.components.SecundaryTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroUsuarioScreen(
    navController: NavController,
    viewModel: AuthViewModel

){

    val estadoRegistroUsuario by viewModel.registrarUsuario.collectAsState()

    val tiposUsuario by viewModel.tiposUsuario.collectAsState()

    var expandido by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarTiposUsuarioApi()
    }

    /*val tipoUsuario = listOf(
        "Admin",
        "Empleado"
    )*/

    Scaffold(
        topBar = {SecundaryTopBar(Routes.UsuariosAdmin, navController)},
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item{
                Text(
                    text = "Registrar Usuario",
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Campos del formulario

            // 1. NOMBRE
            item{
                OutlinedTextField(
                    value = estadoRegistroUsuario.nombre,
                    onValueChange = {viewModel.onNombreChange(it)},
                    label = {Text(text = "Nombre")},
                    isError = estadoRegistroUsuario.errorNombre != null,
                    supportingText = {
                        estadoRegistroUsuario.errorNombre?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 2. APELLIDO
            item{
                OutlinedTextField(
                    value = estadoRegistroUsuario.apellido,
                    onValueChange = {viewModel.onApellidoChange(it)},
                    label = {Text(text = "Apellido")},
                    isError = estadoRegistroUsuario.errorApellido != null,
                    supportingText = {
                        estadoRegistroUsuario.errorApellido?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 3. EMAIL
            item{
                OutlinedTextField(
                    value = estadoRegistroUsuario.email,
                    onValueChange = {viewModel.onEmail1Change(it)},
                    label = {Text(text = "Email")},
                    isError = estadoRegistroUsuario.errorEmail != null,
                    supportingText = {
                        estadoRegistroUsuario.errorEmail?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 4. CONTRASENA
            item{
                OutlinedTextField(
                    value = estadoRegistroUsuario.contrasena,
                    onValueChange = {viewModel.onContrasenaChange(it)},
                    label = {Text(text = "Contraseña")},
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estadoRegistroUsuario.errorContrasena != null,

                    supportingText = {
                        estadoRegistroUsuario.errorContrasena?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // 5. CONFIRMAR
            item {
                OutlinedTextField(
                    value = estadoRegistroUsuario.confirmar,
                    onValueChange = {viewModel.onConfirmarChange(it)},
                    label = {Text(text = "Confirmar")},
                    visualTransformation = PasswordVisualTransformation(),
                    isError = estadoRegistroUsuario.errorConfirmar != null,
                    supportingText = {
                        estadoRegistroUsuario.errorConfirmar?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expandido,
                    onExpandedChange = {expandido = it}
                ){
                    OutlinedTextField(
                        value = estadoRegistroUsuario.tipoUsuario,
                        onValueChange = {viewModel.onTipoUsuarioChange(it)},
                        label = {Text(text = "Elige el Tipo De Usuario")},
                        readOnly = true,
                        isError = estadoRegistroUsuario.errorTipoUsuario != null,
                        supportingText = {
                            estadoRegistroUsuario.errorTipoUsuario?.let{
                                Text(text = it, color = MaterialTheme.colorScheme.error)
                            }
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                        )

                    // Contenedor de los items del ExposedDropdownMenuBox
                    ExposedDropdownMenu(
                        expanded = expandido, // Valor inicial
                        onDismissRequest = {expandido = false} // Cuando se presione afuera del Menu, este se cierra
                    ) {
                        // Opciones -> Loop a los valores del list 'tipoProblemas' para evitar codigo repetitivo
                        tiposUsuario.forEach{ tUsuarioApi ->
                            DropdownMenuItem(
                                text = {Text(text = tUsuarioApi.nombreTipo)},
                                onClick = {
                                    viewModel.actualizarTipoUsuario(tUsuarioApi.nombreTipo, tUsuarioApi.id)
                                    expandido = false
                                }
                            )
                        }
                    }
                }
            }


            // Botones para registrar producto o cancelar registro
            item{
                Row(
                    modifier = Modifier.fillMaxWidth()
                ){
                    // Boton para cancelar
                    Button(
                        // Si se cancela el registro, se limpian los campos
                        onClick = {viewModel.limpiarCamposRegistroUsua()}
                    ) {
                        Text(text = "Cancelar")
                    }

                    // Boton para registrar el usuario
                    Button(
                        onClick = {
                            if(viewModel.canSubmitRegistrarUsua()) {
                                //viewModel.submitRegistroUsuario() // Se registra el usuario
                                viewModel.submitRegistroUsuarioApi() // Se registra usuario en API
                                viewModel.limpiarCamposRegistroUsua() // Se limpian los campos del formulario
                            }
                        }
                    ) {
                        Text(text = "Registrar")
                    }
                }
            }
        }
    }
}