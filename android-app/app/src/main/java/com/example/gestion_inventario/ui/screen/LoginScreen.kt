package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
	navController: NavController,
	viewModel: AuthViewModel
) {

	// Se accede a estado actual desde el ViewModel
	val estadoLogin by viewModel.login.collectAsState() // Se recolectan los valores del flujo StateFlow y se convierten a estados.

	// Remember para estado de animacion de CircularProgressIndicator
	var progresoHabilitado by remember { mutableStateOf(false) }


	Column(
		modifier = Modifier
			.padding(16.dp)
			.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(text = "Inicie sesión", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)

		Spacer(Modifier.size(30.dp))

		// Campo email
		OutlinedTextField(
			value = estadoLogin.email,
			onValueChange = {viewModel.onEmailChange(it)}, // Referencia a 'onEmailChange' -> Se ejecutara cuando cambie el valor del campo 'email'
			label = {Text(text = "Email")},
			isError = estadoLogin.emailError != null, // Si esta condicion es verdadera, debe mostrarse mensaje de error
			supportingText = {
				estadoLogin.emailError?.let {
					Text(
						text = it,
						color = MaterialTheme.colorScheme.error
					)
				}
			},
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		// Campo Password
		OutlinedTextField(
			value = estadoLogin.password,
			onValueChange = {viewModel.onPasswordLoginChange(it)},
			label = {Text(text = "Password")},
			visualTransformation = PasswordVisualTransformation(),
			isError = estadoLogin.passwordError != null,
			supportingText = {
				estadoLogin.passwordError?.let {
					Text(
						text = it,
						color = MaterialTheme.colorScheme.error
					)
				}
			},
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)

		Spacer(Modifier.size(30.dp))

		FilledTonalButton(
			onClick = {
				// Se lanza corrutina porque 'canSubmitLogin()' es un metodo asincrono
				CoroutineScope(Dispatchers.Main).launch {
					val sePuedeIniciarSesion = viewModel.canSubmitLogin()

					if(sePuedeIniciarSesion) {
						progresoHabilitado = true

						delay(2000L) // Simulacion de carga: Demora de 2 segundos

						navController.navigate(route = Routes.HomeAdmin.ruta)

						viewModel.limpiarCamposLogin() // Limpieza de campos
					}
				}
			},
			modifier = Modifier.fillMaxWidth()
		) {
			if(progresoHabilitado){
				CircularProgressIndicator(
					modifier = Modifier.padding(2.dp),
					strokeWidth = 2.dp,
					color = Color.Red
				)
			} else {
				Text(text = "Iniciar sesión")
			}
		}
	}
}