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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.ui.components.MainTopBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.gestion_inventario.validation.*
import com.example.gestion_inventario.navigation.Routes



@Composable
fun AgregarProductoScreen(navController: NavController){

    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var codigo by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    // Estados de errores
    var errorNombre by remember { mutableStateOf<String?>(null) }
    var errorCodigo by remember { mutableStateOf<String?>(null) }
    var errorTalla by remember { mutableStateOf<String?>(null) }
    var errorPrecio by remember { mutableStateOf<String?>(null) }
    var errorCantidad by remember { mutableStateOf<String?>(null) }

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
                text = "Agregar Producto",
                modifier = Modifier.padding(24.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge

            )
            // Nombre
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                isError = errorNombre != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorNombre != null)
                Text(text = errorNombre!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Código
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código") },
                isError = errorCodigo != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorCodigo != null)
                Text(text = errorCodigo!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Talla
            OutlinedTextField(
                value = talla,
                onValueChange = { talla = it },
                label = { Text("Talla") },
                isError = errorTalla != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorTalla != null)
                Text(text = errorTalla!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Precio
            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio (formato 10.000)") },
                isError = errorPrecio != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorPrecio != null)
                Text(text = errorPrecio!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(12.dp))

            // Cantidad
            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                isError = errorCantidad != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (errorCantidad != null)
                Text(text = errorCantidad!!, color = MaterialTheme.colorScheme.error, fontSize = MaterialTheme.typography.bodySmall.fontSize)

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    // Validaciones
                    errorNombre = validateName(nombre)
                    errorCodigo = validateProductCode(codigo)
                    errorTalla = validateShoeSize(talla)
                    errorPrecio = validatePriceCLP(precio)
                    errorCantidad = validateQuantity(cantidad)

                    val hayError = listOf<String?>(
                        errorNombre,
                        errorCodigo,
                        errorTalla,
                        errorPrecio,
                        errorCantidad
                    ).any { it != null }

                    if (!hayError) {
                        navController.popBackStack(Routes.AgregarProducto.ruta, inclusive = true)
                        navController.navigate(Routes.ProductosAdmin.ruta)
                    }

                }) {
                    Text("Agregar")
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

