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
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.UsuarioRepository
//import com.example.gestion_inventario.viewmodel.AuthViewModel
//import com.example.gestion_inventario.viewmodel.AuthViewModelFactory
import com.example.gestion_inventario.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProductoScreen(
    navController: NavController,
    productoId: Int,
    viewModel: ProductoViewModel
    //productoId: Long
) {
    /*val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)

// Repositorios
    val usuarioRepo = UsuarioRepository(db.usuarioDao())
    val productoRepo = ProductoRepository(db.productoDao())

// Factory y ViewModel
    val factory = AuthViewModelFactory(
        usuarioRepository = usuarioRepo,
        productoRepository = productoRepo
    )
    val viewModel: AuthViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = factory)*/

// Estado del usuario
    val producto by viewModel.productoSeleccionado.collectAsState()
    val scope = rememberCoroutineScope()

    // Estado de Menus Dropdown
    var expandidoMenuTalla by remember{mutableStateOf(false)}
    var expandidoMenuColor by remember{mutableStateOf(false)}

    // Estado producto de la API
    val productoAPI by viewModel.productoAPI.collectAsState()

    val colores by viewModel.colores.collectAsState()
    val tallas by viewModel.tallas.collectAsState()

    //val productoSolicitudAPI by viewModel.productoSolicitudAPI.collectAsState()

    // Gestion de estado local para campos de formulario
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    var idTalla by remember { mutableStateOf<Int?>(null) }
    var idColor by remember { mutableStateOf<Int?>(null) }

    
    LaunchedEffect(key1 = productoId) {
        //viewModel.cargarProductoPorId(productoId)
        viewModel.cargarProductoApiPorId(productoId)
    }

    LaunchedEffect(productoAPI) {
        productoAPI?.let { p ->
            nombre = p.nombre
            descripcion = p.descripcion
            precio = p.precio.toString()
            cantidad = p.cantidad.toString()

            idTalla = viewModel.obtenerTallaDeProducto(p.id)?.id
            idColor = viewModel.obtenerColorDeProducto(p.id)?.id
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarColoresApi()
        viewModel.cargarTallasApi()
    }

    // Se obtienen el color y la talla del producto que se va a actualizar
    val colorProducto = viewModel.obtenerColorDeProducto(productoAPI!!.id)
    val tallaProducto = viewModel.obtenerTallaDeProducto(productoAPI!!.id)

    /*
    //var codigo by remember { mutableStateOf("") }
    var nombreProducto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    */


    /*LaunchedEffect(producto) {
        producto?.let {
            codigo = it.codigo
            nombreProducto = it.nombreProducto
            descripcion = it.descripcion
            talla = it.talla
            color = it.color
            precio = it.precio
            cantidad = it.cantidad

        }
    }*/

    //var expanded by remember { mutableStateOf(false) }
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
            /*OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Codigo") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            //Spacer(modifier = Modifier.height(12.dp))

            // 1. Nombre del producto
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(text = "Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )
            /*OutlinedTextField(
                value = nombreProducto,
                onValueChange = { nombreProducto = it },
                label = { Text("Nombre del Producto") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Descripcion del producto
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text(text = "Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            /*OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripcion") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Talla
            ExposedDropdownMenuBox(
                    expanded = expandidoMenuTalla,
                    onExpandedChange = {expandidoMenuTalla = it}
                ) {
                    OutlinedTextField(
                        value = tallas.firstOrNull { it.id == idTalla }?.talla?.toString() ?: "",
                        onValueChange = {},
                        label = {Text(text = "Elige una talla")},
                        readOnly = true,
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
                                    //viewModel.actualizarTalla(tallaApi.talla.toString(), tallaApi.id)
                                    idTalla = tallaApi.id
                                    expandidoMenuTalla = false
                                }
                            )
                        }
                    }
                }
            /*OutlinedTextField(
                value = talla,
                onValueChange = { talla = it },
                label = { Text("Talla") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            // 4. Color
            ExposedDropdownMenuBox(
                    expanded = expandidoMenuColor,
                    onExpandedChange = {expandidoMenuColor = it}
                ) {
                    OutlinedTextField(
                        value = colores.firstOrNull { it.id == idColor }?.color ?: "",
                        onValueChange = {},
                        label = {Text(text = "Elige un color")},
                        readOnly = true,
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
                                    //viewModel.actualizarColor(colorApi.color, colorApi.id)
                                    idColor = colorApi.id
                                    expandidoMenuColor = false
                                }
                            )
                        }
                    }
                }
            /*OutlinedTextField(
                value = color,
                onValueChange = { color = it },
                label = { Text("Color") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = precio,
                onValueChange = { if (it.all(Char::isDigit)) precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            /*OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { if (it.all(Char::isDigit)) cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )

            /*OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(0.9f)
            )*/

            Spacer(modifier = Modifier.height(12.dp))



            // 🔹 Botones centrados
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = {
                    val productoActualizar = ProductoSolicitud(
                        nombre = nombre,
                        descripcion = descripcion,
                        precio = precio.toInt(),
                        cantidad = cantidad.toInt(),
                        talla = IdObject(idTalla!!),
                        color = IdObject(idColor!!)
                    )

                    viewModel.submitActualizarProductoAPI(productoId, productoActualizar)
                    /*producto?.let {
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
                    }*/
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
