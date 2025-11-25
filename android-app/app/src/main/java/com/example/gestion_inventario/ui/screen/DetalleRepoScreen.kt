package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gestion_inventario.data.remote.model.ReporteProblemaAPI
import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.ui.components.MainDrawer
import com.example.gestion_inventario.ui.components.SecundaryTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModelReporte
import kotlinx.coroutines.launch

@Composable
fun DetalleRepoScreen (
    navController: NavController,
    viewModel: AuthViewModelReporte,
    problemaId:  Int

){
    val reporteProblemaAPI by viewModel.reporteApi.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    LaunchedEffect(problemaId) {
        //viewModel.cargarProductoPorId(productoId)
        viewModel.cargarReporteApiPorId(problemaId)
    }

    MainDrawer(navController, drawerState, scope) {
        Scaffold(
            topBar = {SecundaryTopBar(Routes.ReportarProblema, navController)}
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(24.dp)
                    .fillMaxSize()
            ) {
                //usuario?.let { usuario ->
                reporteProblemaAPI?.let { reporteProblemaAPI ->
                    val tipoProblema = viewModel.obtenerTipoProblema(problemaId)
                    val nivelProblema = viewModel.obtenerNivelPrioridad(problemaId)

                    Text(
                        text = "Correo: ${reporteProblemaAPI.correo}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "Tipo: ${tipoProblema?.tipoProblema}")
                    Text(text = "Descripcion: ${reporteProblemaAPI.descripcion}")
                    Text(text = "Prioridad: ${nivelProblema?.nivelPrioridad}")


                    Spacer(modifier = Modifier.height(24.dp))

                    // 🔹 Botón para editar
                    Button(
                        onClick = {
                            navController.navigate("editarReporte/${reporteProblemaAPI.id}")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Editar Reporte")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 🔹 Botón para eliminar
                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.submitEliminarProblemaAPI(problemaId)
                                navController.popBackStack()
                                //viewModel.eliminarUsuario(usuario.id)
                                //navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Eliminar Reporte")
                    }
                } ?: Text(text = "Cargando reporte...")
            }
        }
    }

}