package com.example.gestion_inventario.ui.components

import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

import com.example.gestion_inventario.R
import com.example.gestion_inventario.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecundaryTopBar(
	ruta: Routes,
	navController: NavController
) {
	TopAppBar(
		title = { },
		navigationIcon = {
			IconButton(onClick = {navController.navigate(getDestino(ruta))}) {
				//Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "Icono de Flecha hacia atrás")
				Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icono de Volver")
			}
		}
	)
}

private fun getDestino(ruta: Routes): String {
    return when (ruta) {
    	is Routes.Login -> Routes.Login.ruta
    	is Routes.HomeAdmin -> Routes.HomeAdmin.ruta
    	is Routes.ProductosAdmin -> Routes.ProductosAdmin.ruta
    	is Routes.UsuariosAdmin -> Routes.UsuariosAdmin.ruta
    	is Routes.ReportarProblema -> Routes.ReportarProblema.ruta
		is Routes.Reportar -> Routes.Reportar.ruta
		is Routes.DetalleProblema -> Routes.DetalleProblema.ruta
		is Routes.EditarReporte -> Routes.EditarReporte.ruta
    	is Routes.AgregarProducto -> Routes.AgregarProducto.ruta
    	is Routes.RegistrarUsuario -> Routes.RegistrarUsuario.ruta
    	is Routes.PerfilUsuario -> Routes.PerfilUsuario.ruta
		is Routes.DetalleUsuario  -> Routes.DetalleUsuario.ruta
		is Routes.DetalleProducto -> Routes.DetalleProducto.ruta
    }
} 