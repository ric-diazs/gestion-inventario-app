package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.TicketReporteAPI
import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.viewmodel.AuthViewModelReporte

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarRepoScreen(
    navController: NavController,
    viewModel: AuthViewModelReporte,
    problemaId: Int
){

    var expandidoMenuTipoProblema by remember { mutableStateOf(false) }
    var expandidoMenuNivelPrioridad by remember { mutableStateOf(false ) }

    val TipoProblema by viewModel.tiposproblema.collectAsState()
    val NivelPrioridad by viewModel.nivelPrioridad.collectAsState()

    val reporteAPI by viewModel.reporteApi.collectAsState()

    var correo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var idTipoProblema by remember { mutableStateOf<Int?>(null) }
    var idNivelPrioridad by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(problemaId) {
        viewModel.cargarReporteApiPorId(problemaId)
    }

    LaunchedEffect(reporteAPI) {
        reporteAPI?.let { r ->
            correo = r.correo
            descripcion = r.descripcion
            idTipoProblema = viewModel.obtenerTipoProblema(r.id)?.id
            idNivelPrioridad = viewModel.obtenerNivelPrioridad(r.id)?.id
        }
    }

    LaunchedEffect(Unit) {
        viewModel.cargarTipoProblemaApi()
        viewModel.cargarNivelPrioridadApi()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Editar Reporte") })
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("correo del usuario") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expandidoMenuTipoProblema,
                onExpandedChange = { expandidoMenuTipoProblema = it }
            ) {
                OutlinedTextField(
                    value = TipoProblema.firstOrNull { it.id == idTipoProblema }?.tipoProblema ?: "",
                    onValueChange = {},
                    label = { Text("Elige el tipo de problema") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoMenuTipoProblema)
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandidoMenuTipoProblema,
                    onDismissRequest = { expandidoMenuTipoProblema = false }
                ) {
                    TipoProblema.forEach { tipo ->
                        DropdownMenuItem(
                            text = { Text(tipo.tipoProblema) },
                            onClick = {
                                idTipoProblema = tipo.id
                                expandidoMenuTipoProblema = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.size(7.dp))

            ExposedDropdownMenuBox(
                expanded = expandidoMenuNivelPrioridad,
                onExpandedChange = { expandidoMenuNivelPrioridad = it }
            ) {
                OutlinedTextField(
                    value = NivelPrioridad.firstOrNull { it.id == idNivelPrioridad }?.nivelPrioridad ?: "",
                    onValueChange = {},
                    label = { Text("Elige el nivel de prioridad") },
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoMenuNivelPrioridad)
                    },
                    modifier = Modifier
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                        .fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expandidoMenuNivelPrioridad,
                    onDismissRequest = { expandidoMenuNivelPrioridad = false }
                ) {
                    NivelPrioridad.forEach { nivel ->
                        DropdownMenuItem(
                            text = { Text(nivel.nivelPrioridad) },
                            onClick = {
                                idNivelPrioridad = nivel.id
                                expandidoMenuNivelPrioridad = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Button(onClick = {
                    val reporteActualizar = TicketReporteAPI(
                        correo = correo,
                        descripcion = descripcion,
                        tipoProblema = IdObject(idTipoProblema!!),
                        nivelPrioridad = IdObject(idNivelPrioridad!!)
                    )

                    viewModel.submitActualizarReporteAPI(problemaId, reporteActualizar)
                }) {
                    Text("Guardar")
                }

                Button(onClick = { navController.navigate(Routes.ReportarProblema.ruta) }) {
                    Text("Cancelar")
                }
            }
        }
    }
}