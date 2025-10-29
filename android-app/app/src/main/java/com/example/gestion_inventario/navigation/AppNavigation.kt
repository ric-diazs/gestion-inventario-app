package com.example.gestion_inventario.navigation

import androidx.compose.foundation.layout.Row
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.example.gestion_inventario.ui.screen.AdminHomeScreen
import com.example.gestion_inventario.ui.screen.AgregarProductoScreen
import com.example.gestion_inventario.ui.screen.DetalleUsuarioScreen
import com.example.gestion_inventario.ui.screen.EditarUsuarioScreen
import com.example.gestion_inventario.ui.screen.LoginScreen
import com.example.gestion_inventario.ui.screen.ProductosAdminScreen
import com.example.gestion_inventario.ui.screen.RegistroUsuarioScreen
import com.example.gestion_inventario.ui.screen.UsuariosAdminScreen
import com.example.gestion_inventario.ui.screen.ReportarProblemaScreen
import com.example.gestion_inventario.ui.screen.PerfilUsuarioScreen
import com.example.gestion_inventario.viewmodel.AuthViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
	val navController = rememberNavController()

	// Se pueden agregar los viewModels aca
	//val authViewModel: AuthViewModel = viewModel()

	// Implementacion de navegaciones
	NavHost(
		navController = navController,
		startDestination = Routes.Login.ruta
	) {
		composable(route = Routes.HomeAdmin.ruta) {
			AdminHomeScreen(navController, authViewModel)
		}

		composable(route = Routes.ProductosAdmin.ruta) {
			ProductosAdminScreen(navController)
		}

		composable(route = Routes.UsuariosAdmin.ruta) {
			UsuariosAdminScreen(navController)
		}

		composable(route = Routes.ReportarProblema.ruta) {
			ReportarProblemaScreen(navController, authViewModel)
		}

		composable(route = Routes.Login.ruta) {
			LoginScreen(navController, authViewModel)
		}

		composable(route = Routes.AgregarProducto.ruta) {
			AgregarProductoScreen(navController, authViewModel)
		}

		composable(route = Routes.RegistrarUsuario.ruta) {
			RegistroUsuarioScreen(navController, authViewModel)
		}

		composable(route = Routes.PerfilUsuario.ruta) {
			PerfilUsuarioScreen(navController, authViewModel)
		}

		composable(
			route = "detalleUsuario/{usuarioId}",
			arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })
		) { backStackEntry ->
			val usuarioId = backStackEntry.arguments?.getLong("usuarioId") ?: 0L
			DetalleUsuarioScreen(navController, usuarioId)
		}

		composable(
			route = "editarUsuario/{usuarioId}",
			arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })
		) { backStackEntry ->
			val usuarioId = backStackEntry.arguments?.getLong("usuarioId") ?: 0L
			EditarUsuarioScreen(navController, usuarioId)
		}

	}

}