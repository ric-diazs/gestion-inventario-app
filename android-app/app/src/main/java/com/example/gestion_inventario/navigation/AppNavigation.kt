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
import com.example.gestion_inventario.ui.screen.DetalleProductoScreen
import com.example.gestion_inventario.ui.screen.DetalleRepoScreen
import com.example.gestion_inventario.ui.screen.DetalleUsuarioScreen
import com.example.gestion_inventario.ui.screen.EditarProductoScreen
import com.example.gestion_inventario.ui.screen.EditarRepoScreen
import com.example.gestion_inventario.ui.screen.EditarUsuarioScreen
import com.example.gestion_inventario.ui.screen.FormularioRepoScreen
import com.example.gestion_inventario.ui.screen.LoginScreen
import com.example.gestion_inventario.ui.screen.ProductosAdminScreen
import com.example.gestion_inventario.ui.screen.RegistroUsuarioScreen
import com.example.gestion_inventario.ui.screen.UsuariosAdminScreen
import com.example.gestion_inventario.ui.screen.PerfilUsuarioScreen
import com.example.gestion_inventario.ui.screen.ReporteProblemaFormScreen
import com.example.gestion_inventario.viewmodel.AuthViewModel
import com.example.gestion_inventario.viewmodel.AuthViewModelReporte
import com.example.gestion_inventario.viewmodel.ProductoViewModel

@Composable
fun AppNavigation(authViewModel: AuthViewModel, prodViewModel: ProductoViewModel, authViewModelReporte: AuthViewModelReporte) {
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
			//ProductosAdminScreen(navController)
			ProductosAdminScreen(navController, prodViewModel)
		}

		composable(route = Routes.UsuariosAdmin.ruta) {
			UsuariosAdminScreen(navController, authViewModel)
		}

		composable(route = Routes.Reportar.ruta) {
			FormularioRepoScreen(navController, authViewModel, authViewModelReporte)
		}

		composable(route = Routes.ReportarProblema.ruta) {
			ReporteProblemaFormScreen(navController,authViewModelReporte)
		}

		composable(route = Routes.Login.ruta) {
			LoginScreen(navController, authViewModel)
		}

		composable(route = Routes.AgregarProducto.ruta) {
			//AgregarProductoScreen(navController, authViewModel)
			AgregarProductoScreen(navController, prodViewModel)
		}

		composable(route = Routes.RegistrarUsuario.ruta) {
			RegistroUsuarioScreen(navController, authViewModel)
		}

		composable(route = Routes.PerfilUsuario.ruta) {
			PerfilUsuarioScreen(navController, authViewModel)
		}

		composable(
			route = "detalleUsuario/{usuarioId}",
			//arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })
			arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
		) { backStackEntry ->
			val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
			DetalleUsuarioScreen(navController, authViewModel, usuarioId)
		}

		composable(
			route = "editarUsuario/{usuarioId}",
			//arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })
			arguments = listOf(navArgument("usuarioId") { type = NavType.IntType })
		) { backStackEntry ->
			val usuarioId = backStackEntry.arguments?.getInt("usuarioId") ?: 0
			EditarUsuarioScreen(navController, authViewModel, usuarioId)
		}
		
		composable(
			route = "detalleProducto/{productoId}",
			arguments = listOf(navArgument("productoId") { type = NavType.IntType })
			//arguments = listOf(navArgument("productoId") { type = NavType.LongType })
		) { backStackEntry ->
			//val productoId = backStackEntry.arguments?.getLong("productoId") ?: 0L
			val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
			DetalleProductoScreen(navController, productoId, prodViewModel)
		}

		composable(
			route = "editarProducto/{productoId}",
			//arguments = listOf(navArgument("productoId") { type = NavType.LongType })
			arguments = listOf(navArgument("productoId") { type = NavType.IntType })
		) { backStackEntry ->
			//val productoId = backStackEntry.arguments?.getLong("productoId") ?: 0L
			val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
			EditarProductoScreen(navController, productoId, prodViewModel)
			//EditarProductoScreen(navController, productoId)
		}

		composable(
			route = "detalleProblema/{reporteId}",
			//arguments = listOf(navArgument("usuarioId") { type = NavType.LongType })
			arguments = listOf(navArgument("reporteId") { type = NavType.IntType })
		) { backStackEntry ->
			val reporteId = backStackEntry.arguments?.getInt("reporteId") ?: 0
			DetalleRepoScreen(navController,authViewModelReporte, reporteId)
		}

		composable(
			route = "editarReporte/{reporteId}",
			arguments = listOf(navArgument("reporteId") { type = NavType.IntType })
		) { backStackEntry ->
			val reporteId = backStackEntry.arguments?.getInt("reporteId") ?: 0
			EditarRepoScreen(navController, authViewModelReporte, reporteId  )

		}

	}

}