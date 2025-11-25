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

	data object  DetalleUsuario : Routes(ruta = "detalleUsuario")

	data object  DetalleProducto : Routes(ruta = "detalleProducto")

	data object  Reportar : Routes(ruta = "reportar")

	data object DetalleProblema: Routes(ruta = "detalleProblema")

	data object  EditarReporte: Routes(ruta = "editarReporte")
}
