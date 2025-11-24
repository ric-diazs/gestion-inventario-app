package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.navigation.Routes


import com.example.gestion_inventario.ui.components.MainDrawer
import com.example.gestion_inventario.ui.components.MainTopBar
//import com.example.gestion_inventario.viewmodel.AuthViewModel
//import com.example.gestion_inventario.viewmodel.AuthViewModelFactory
import com.example.gestion_inventario.viewmodel.ProductoViewModel


@Composable
fun ProductosAdminScreen(
	navController: NavController,
	viewModel: ProductoViewModel
) {

	/*val context = LocalContext.current
	val db = AppDatabase.getDatabase(context)
	val usuarioRepo = UsuarioRepository(db.usuarioDao())
	val productoRepo = ProductoRepository(db.productoDao())

	val factory = AuthViewModelFactory(usuarioRepo, productoRepo)
	val viewModel: AuthViewModel = viewModel(factory = factory)*/


	val productos by viewModel.productos.collectAsState()

	val productosAPI by viewModel.productosAPI.collectAsState()

	LaunchedEffect(Unit) {
		//viewModel.cargarProductos()
		viewModel.cargarProductosApi()
	}


	// Variables para menu Drawer (Falta importar dependencias en header)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Implementacion de Drawer
    MainDrawer(navController, drawerState, scope){    
    	Scaffold(
    		topBar = {MainTopBar(navController, drawerState, scope)}
    		//topBar = {MainTopBar(navController, drawerState, scope, viewModel)}
    	){ innerPadding ->
    		Column(
    			modifier = Modifier
    				.padding(innerPadding)
    				.fillMaxSize(),
    			horizontalAlignment = Alignment.CenterHorizontally
    		){
    			Text(
    				text = "Productos Registrados",
    				modifier = Modifier.padding(24.dp),
    	            fontWeight = FontWeight.Bold,
    	            textAlign = TextAlign.Center,
    	            style = MaterialTheme.typography.titleLarge
    			)
    
    			Button(
    				//onClick = { navController.navigate(Routes.AgregarProducto.ruta) },
    				onClick = {navController.navigate(Routes.AgregarProducto.ruta)},
    				modifier = Modifier
    					.padding(top = 8.dp)
    					.fillMaxWidth(0.6f)
    					.height(50.dp)
    			) {
    				Icon(
    					imageVector = Icons.Filled.Add,
    					contentDescription = "Agregar Producto",
    					modifier = Modifier.padding(end = 8.dp)
    				)
    				Text(text = "Agregar Producto")
    			}

				Spacer(modifier = Modifier.height(16.dp))

				// Mostrar lista o mensaje vacío
				//if (productos.isEmpty()) {
				if(productosAPI.isEmpty()) {
					Text(
						text = "No hay productos registrados",
						style = MaterialTheme.typography.bodyLarge,
						textAlign = TextAlign.Center,
						modifier = Modifier.padding(16.dp)
					)
				} else {
					LazyColumn(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp)
					) {
						items(productosAPI) { productoAPI ->
							val color = viewModel.obtenerColorDeProducto(productoAPI.id)
							val talla = viewModel.obtenerTallaDeProducto(productoAPI.id)

							Card(
								modifier = Modifier
									.fillMaxWidth()
									.padding(vertical = 8.dp)
									.clickable{
										navController.navigate("detalleProducto/${productoAPI.id}")
									},
								elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
							){
								Column(modifier = Modifier.padding(16.dp)){
									Text(text = "${productoAPI.nombre}", fontWeight = FontWeight.Bold)
									Text(text = "${productoAPI.descripcion}")
									Text(text = "Talla: ${talla?.talla ?: "Desconocida"}")
									Text(text = "Color: ${color?.color ?: "Desconocido"}")
									Text(text = "Precio: $${productoAPI.precio}")
									Text(text = "Cantidad: ${productoAPI.cantidad}")
								}
							}
						}
						/*items(productos) { producto ->
							Card(
								modifier = Modifier
									.fillMaxWidth()
									.padding(vertical = 8.dp)
									.clickable{
										navController.navigate("detalleProducto/${producto.id}")
									},
								elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
							) {
								Column(modifier = Modifier.padding(16.dp)) {
									Text(
										text = "${producto.codigo} ${producto.nombreProducto}",
										fontWeight = FontWeight.Bold
									)
									Text(text = producto.descripcion)
									Text(text = "Tipo: ${producto.talla}")
									Text(text = producto.color)
									Text(text = "Tipo: ${producto.precio}")
									Text(text = producto.cantidad)
								}
							}
						}*/
					}
				}
    		}
    	}
    }
}