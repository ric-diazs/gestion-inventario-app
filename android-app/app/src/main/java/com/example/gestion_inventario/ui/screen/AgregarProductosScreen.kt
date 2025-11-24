package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.gestion_inventario.ui.components.SecundaryTopBar
//import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarProductoScreen(
    navController: NavController,
    viewModel: ProductoViewModel
    //viewModel: AuthViewModel
){
    // Variable para gestionar estado de los atributos de AgregarProductoUIState
    val estadoAgregarProducto by viewModel.registrarProducto.collectAsState()

    // Gestion de estado de colores
    val colores by viewModel.colores.collectAsState()

    // Gestion de estado de tallas
    val tallas by viewModel.tallas.collectAsState()

    // Se recuerda a nivel de la clase el estado para el ExposedDropdownMenuBox
    var expandidoMenuColor by remember{ mutableStateOf(false) }

    var expandidoMenuTalla by remember{ mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.cargarColoresApi()
        viewModel.cargarTallasApi()
    }

    Scaffold(
        topBar = {SecundaryTopBar(Routes.ProductosAdmin, navController)},
        modifier = Modifier.fillMaxSize()
    ){ innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            item {
                Text(
                    text = "Registrar Producto",
                    modifier = Modifier.padding(24.dp),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // Campos del formulario

            // 1. Codigo
            /*item {
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
            }*/

            // 2. Nombre producto
            item{
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
            }

            // 3. Descripcion
            item{
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
            }

            // 4. Talla
            item{
                ExposedDropdownMenuBox(
                    expanded = expandidoMenuTalla,
                    onExpandedChange = {expandidoMenuTalla = it}
                ) {
                    OutlinedTextField(
                        value = estadoAgregarProducto.talla,
                        onValueChange = {viewModel.onTallaChange(it)},
                        label = {Text(text = "Elige una talla")},
                        readOnly = true,
                        isError = estadoAgregarProducto.errorTalla != null,
                        supportingText = {
                            estadoAgregarProducto.errorTalla?.let {
                                Text(
                                    text = it, color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoMenuTalla)},
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expandidoMenuTalla,
                        onDismissRequest = {expandidoMenuTalla = false}
                    ) {
                        tallas.forEach { tallaApi ->
                            DropdownMenuItem(
                                text = {Text(text = tallaApi.talla.toString())},
                                onClick = {
                                    viewModel.actualizarTalla(tallaApi.talla.toString(), tallaApi.id)
                                    expandidoMenuTalla = false
                                }
                            )
                        }
                    }
                }
            }

            // 5. Color
            item {
                ExposedDropdownMenuBox(
                    expanded = expandidoMenuColor,
                    onExpandedChange = {expandidoMenuColor = it}
                ) {
                    OutlinedTextField(
                        value = estadoAgregarProducto.color,
                        onValueChange = {viewModel.onColorChange(it)},
                        label = {Text(text = "Elige un color")},
                        readOnly = true,
                        isError = estadoAgregarProducto.errorColor != null,
                        supportingText = {
                            estadoAgregarProducto.errorColor?.let {
                                Text(
                                    text = it, color = MaterialTheme.colorScheme.error
                                )
                            }
                        },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoMenuColor)},
                        modifier = Modifier
                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                            .fillMaxWidth()
                    )

                   ExposedDropdownMenu(
                        expanded = expandidoMenuColor,
                        onDismissRequest = {expandidoMenuColor = false}
                    ) {
                        colores.forEach { colorApi ->
                            DropdownMenuItem(
                                text = {Text(text = colorApi.color)},
                                onClick = {
                                    viewModel.actualizarColor(colorApi.color, colorApi.id)

                                    expandidoMenuColor = false
                                }
                            )
                        }
                    }
                }
            }

            // 6. Precio
            item{
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
            }

            // 7. Cantidad
            item {
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
            }


            // Botones para registrar producto o cancelar registro
            item {
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
                                //viewModel.submitRegistroProducto() // Se registra el producto
                                viewModel.submitRegistroProductoAPI() // Registro de producto en API
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
}