package com.example.gestion_inventario.model

data class LoginUiState(
	// Estado de pantalla
	val email: String = "",
	val password: String = "",

	// Manejo de errores
	val emailError: String? = null,
	val passwordError: String? = null
)
