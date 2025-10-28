package com.example.gestion_inventario.navigation


import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.gestion_inventario.ui.screen.AdminHomeScreen
import com.example.gestion_inventario.ui.screen.ProductosAdminScreen
import com.example.gestion_inventario.ui.screen.UsuariosAdminScreen
import com.example.gestion_inventario.ui.screen.ReportarProblemaScreen
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.RegistroRepository
import com.example.gestion_inventario.data.repository.ReporteRepository
import com.example.gestion_inventario.ui.screen.AgregarProductoScreenVm

import com.example.gestion_inventario.ui.screen.RegistroUsuarioAdminScreenVm
import com.example.gestion_inventario.ui.screen.ReportesScreenVm
import com.example.gestion_inventario.ui.viewmodel.ProductoAdminViewModel
import com.example.gestion_inventario.ui.viewmodel.ProductoAdminViewModelFactory
import com.example.gestion_inventario.ui.viewmodel.RegistroAdminViewModel
import com.example.gestion_inventario.ui.viewmodel.RegistroAdminViewModelFactory
import com.example.gestion_inventario.ui.viewmodel.ReportarProblemaViewModel
import com.example.gestion_inventario.ui.viewmodel.ReportesScreenViewModelFactory

@Composable
fun AppNavigation(
	navController: NavHostController,
	registroAdminViewModel: RegistroAdminViewModel
) {
	// Obtenemos la base de datos y el DAO
	val context = LocalContext.current
	val db = AppDatabase.getDatabase(context)
	val RegistroDao = db.registroDao()
	val registroRepository = RegistroRepository(RegistroDao)

	// Se pueden agregar los viewModels aca

	// Implementacion de navegaciones
	NavHost(
		navController = navController,
		startDestination = Routes.HomeAdmin.ruta
	) {
		composable(route = Routes.HomeAdmin.ruta) {
			AdminHomeScreen(navController)
		}

		composable(route = Routes.ProductosAdmin.ruta) {
			ProductosAdminScreen(navController)
		}

		composable(route = Routes.UsuariosAdmin.ruta) {
			UsuariosAdminScreen(navController)
		}

		composable(route = Routes.ReportarProblema.ruta) {
			ReportarProblemaScreen(navController)
		}

		composable(route = Routes.AgregarProducto.ruta) {
			val context = LocalContext.current
			val db = AppDatabase.getDatabase(context)
			val productoRepository = ProductoRepository(db.productoDao())

			val productoAdminViewModel: ProductoAdminViewModel = viewModel(
				factory = ProductoAdminViewModelFactory(productoRepository)
			)
			AgregarProductoScreenVm(
				vm = productoAdminViewModel,
				onSuccessNavigate = {
					// Ejemplo: volver a la pantalla anterior o ir al home
					navController.navigate(Routes.HomeAdmin.ruta) {
						popUpTo(Routes.AgregarProducto.ruta) { inclusive = true }
					}
				},
				onCancel = {
					navController.popBackStack()
				}
			)

		}

		composable(route = Routes.ReportesScreen.ruta) {
			val context = LocalContext.current
			val db = AppDatabase.getDatabase(context)
			val reporteRepository = ReporteRepository(db.reporteDao())

			val reportarProblemaViewModel: ReportarProblemaViewModel = viewModel(
				factory = ReportesScreenViewModelFactory(reporteRepository)
			)
			ReportesScreenVm(
				vm = reportarProblemaViewModel,
				onSuccessNavigate = {
					// Ejemplo: volver a la pantalla anterior o ir al home
					navController.navigate(Routes.HomeAdmin.ruta) {
						popUpTo(Routes.ReportesScreen.ruta) { inclusive = true }
					}
				},
				onCancel = {
					navController.popBackStack()
				}
			)

		}


		composable(route = Routes.RegistroUsuarioAdmin.ruta) {

			// 🔹 1. Obtener el ViewModel con su factory
			val context = LocalContext.current
			val db = AppDatabase.getDatabase(context)
			val registroRepository = RegistroRepository(db.registroDao())

			val registroAdminViewModel: RegistroAdminViewModel = viewModel(
				factory = RegistroAdminViewModelFactory(registroRepository)
			)
			RegistroUsuarioAdminScreenVm(
				vm = registroAdminViewModel,
				onSuccessNavigate = {
					// Ejemplo: volver a la pantalla anterior o ir al home
					navController.navigate(Routes.HomeAdmin.ruta) {
						popUpTo(Routes.RegistroUsuarioAdmin.ruta) { inclusive = true }
					}
				},
				onCancel = {
					navController.popBackStack()
				}
			)
		}
	}

}