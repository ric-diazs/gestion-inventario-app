package com.example.gestion_inventario.navigation

sealed class Routes(val ruta: String) {
	data object Login : Routes(ruta = "login")
	data object HomeAdmin : Routes(ruta = "homeAdmin")
	data object ProductosAdmin : Routes(ruta ="productosAdmin")
	data object UsuariosAdmin : Routes(ruta ="usuariosAdmin")
	data object ReportarProblema : Routes(ruta ="reportarProblema")

	data object AgregarProducto : Routes(ruta ="agregarProducto")

	data object RegistrarUsuario : Routes(ruta ="registrarUsuario")

	data object PerfilUsuario : Routes(ruta ="perfilUsuario")
}
