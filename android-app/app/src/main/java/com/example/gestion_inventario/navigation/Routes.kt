package com.example.gestion_inventario.navigation

sealed class Routes(val ruta: String) {
	data object HomeAdmin : Routes(ruta = "homeAdmin")
	data object ProductosAdmin : Routes(ruta ="productosAdmin")
	data object UsuariosAdmin : Routes(ruta ="usuariosAdmin")
	data object ReportarProblema : Routes(ruta ="reportarProblema")
}
