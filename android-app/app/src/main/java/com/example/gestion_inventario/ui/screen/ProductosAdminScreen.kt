package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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

import com.example.gestion_inventario.ui.components.MainTopBar


@Composable
fun ProductosAdminScreen(
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
				text = "Productos Registrados",
				modifier = Modifier.padding(24.dp),
	            fontWeight = FontWeight.Bold,
	            textAlign = TextAlign.Center,
	            style = MaterialTheme.typography.titleLarge
			)
		}
	}
}