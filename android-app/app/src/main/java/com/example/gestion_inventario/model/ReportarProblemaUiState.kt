package com.example.gestion_inventario.model

data class ReportarProblemaUiState(
	val email: String = "",
	val tipoProblema: String = "",
	val nivelProblema: String = "",
	val detalleProblema: String = "",

	// Errores (El detalle de problema no sera un campo obligatorio, asi que no se validara)
	val emailError: String? = null,
	val tipoProblemaError: String? = null,
	val nivelProblemaError: String? = null
)