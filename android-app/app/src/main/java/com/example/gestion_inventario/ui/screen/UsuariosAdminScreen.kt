package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.navigation.Routes

import com.example.gestion_inventario.ui.components.MainTopBar


@Composable
fun UsuariosAdminScreen(
	navController: NavController
) {
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
				text = "Usuarios del Sistema",
				modifier = Modifier.padding(24.dp),
	            fontWeight = FontWeight.Bold,
	            textAlign = TextAlign.Center,
	            style = MaterialTheme.typography.titleLarge
			)

			Button(
				//onClick = { navController.navigate(Routes.RegistrarUsuario.ruta) },
				onClick = {navController.navigate(Routes.RegistrarUsuario.ruta)},
				modifier = Modifier
					.padding(top = 8.dp)
					.fillMaxWidth(0.6f)
					.height(50.dp)
			) {
				Icon(
					imageVector = Icons.Filled.Add,
					contentDescription = "Agregar Usuario",
					modifier = Modifier.padding(end = 8.dp)
				)
				Text(text = "Agregar Usuario")
			}
		}
	}
}