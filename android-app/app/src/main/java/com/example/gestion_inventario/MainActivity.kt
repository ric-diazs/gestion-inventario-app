package com.example.gestion_inventario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.gestion_inventario.data.local.database.AppDatabase
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.RegistroRepository
import com.example.gestion_inventario.data.repository.ReporteRepository
import com.example.gestion_inventario.navigation.AppNavigation
import com.example.gestion_inventario.ui.theme.Gestion_inventarioTheme
import com.example.gestion_inventario.ui.viewmodel.ProductoAdminViewModel
import com.example.gestion_inventario.ui.viewmodel.ProductoAdminViewModelFactory
import com.example.gestion_inventario.ui.viewmodel.RegistroAdminViewModel
import com.example.gestion_inventario.ui.viewmodel.RegistroAdminViewModelFactory
import com.example.gestion_inventario.ui.viewmodel.ReportarProblemaViewModel
import com.example.gestion_inventario.ui.viewmodel.ReportesScreenViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current.applicationContext
            val db = AppDatabase.getDatabase(context)

            // DAOs
            val registroDao = db.registroDao()
            val productoDao = db.productoDao()
            val reporteDao = db.reporteDao()

            // ✅ Crear Repositories
            val registroRepository = RegistroRepository(registroDao)
            val productoRepository = ProductoRepository(productoDao)
            val reporteRepository = ReporteRepository(reporteDao)

            // ✅ ViewModels con Factory
            val registroViewModel: RegistroAdminViewModel = viewModel(
                factory = RegistroAdminViewModelFactory(registroRepository)
            )

            val productoViewModel: ProductoAdminViewModel = viewModel(
                factory = ProductoAdminViewModelFactory(productoRepository)
            )

            val reporteViewModel: ReportarProblemaViewModel = viewModel(
                factory = ReportesScreenViewModelFactory(reporteRepository)
            )

            // ✅ Controlador de navegación
            val navController = rememberNavController()

            Gestion_inventarioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        navController = navController,
                        registroAdminViewModel = registroViewModel
                    )
                }
            }
        }
    }
}
