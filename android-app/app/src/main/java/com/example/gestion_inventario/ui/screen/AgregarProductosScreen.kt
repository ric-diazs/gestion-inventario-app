package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.gestion_inventario.viewmodel.AuthViewModel

@Composable
fun AgregarProductoScreen(
    navController: NavController,
    viewModel: AuthViewModel
){
    // Variable para gestionar estado de los atributos de AgregarProductoUIState
    val estadoAgregarProducto by viewModel.registrarProducto.collectAsState()

    Scaffold(
        topBar = {},
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Registrar Producto",
                modifier = Modifier.padding(24.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            // Campos del formulario

            // 1. Codigo
            OutlinedTextField(
                value = estadoAgregarProducto.codigo,
                onValueChange = {viewModel.onCodigoChange(it)},
                label = {Text(text = "Código")},
                isError = estadoAgregarProducto.errorCodigo != null,
                supportingText = {
                    estadoAgregarProducto.errorCodigo?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 2. Nombre producto
            OutlinedTextField(
                value = estadoAgregarProducto.nombreProducto,
                onValueChange = {viewModel.onNombreProductoChange(it)},
                label = {Text(text = "Nombre del producto")},
                isError = estadoAgregarProducto.errorNombreProducto != null,
                supportingText = {
                    estadoAgregarProducto.errorNombreProducto?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 3. Descripcion
            OutlinedTextField(
                value = estadoAgregarProducto.descripcion,
                onValueChange = {viewModel.onDescripcionChange(it)},
                label = {Text(text = "Descripción del producto")},
                isError = estadoAgregarProducto.errorDescripcion != null,
                supportingText = {
                    estadoAgregarProducto.errorDescripcion?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 4. Talla
            OutlinedTextField(
                value = estadoAgregarProducto.talla,
                onValueChange = {viewModel.onTallaChange(it)},
                label = {Text(text = "Talla")},
                isError = estadoAgregarProducto.errorTalla != null,
                supportingText = {
                    estadoAgregarProducto.errorTalla?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 5. Color
            OutlinedTextField(
                value = estadoAgregarProducto.color,
                onValueChange = {viewModel.onColorChange(it)},
                label = {Text(text = "Color")},
                isError = estadoAgregarProducto.errorColor != null,
                supportingText = {
                    estadoAgregarProducto.errorColor?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 6. Precio
            OutlinedTextField(
                value = estadoAgregarProducto.precio,
                onValueChange = {viewModel.onPrecioChange(it)},
                label = {Text(text = "Precio")},
                isError = estadoAgregarProducto.errorPrecio != null,
                supportingText = {
                    estadoAgregarProducto.errorPrecio?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 7. Cantidad
            OutlinedTextField(
                value = estadoAgregarProducto.cantidad,
                onValueChange = {viewModel.onCantidadChange(it)},
                label = {Text(text = "Cantidad")},
                isError = estadoAgregarProducto.errorCantidad != null,
                supportingText = {
                    estadoAgregarProducto.errorCantidad?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )


            // Botones para registrar producto o cancelar registro
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                // Boton para cancelar
                Button(
                    // Si se cancela el registro, se limpian los campos
                    onClick = {viewModel.limpiarCamposRegistroProd()}
                ) {
                    Text(text = "Cancelar")
                }

                // Boton para registrar el producto
                Button(
                    onClick = {
                        if(viewModel.canSubmitRegistrarProd()) {
                            viewModel.submitRegistroProducto() // Se registra el producto
                            viewModel.limpiarCamposRegistroProd() // Se limpian los campos del formulario
                        }
                    }
                ) {
                    Text(text = "Registrar")
                }
            }
        }
    }
}