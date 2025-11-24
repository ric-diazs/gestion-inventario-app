package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

import com.example.gestion_inventario.ui.components.MainDrawer
import com.example.gestion_inventario.ui.components.MainTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class) // Para que funcione ExposedDropdownMenuBox
@Composable
fun ReportarProblemaScreen(
	navController: NavController,
	viewModel: AuthViewModel
) {
	// Acceso a valor actual del estado del usuario logueado a traves del ViewModel
	// Se usa para acceder a su correo
    val estadoUsuario = viewModel.usuarioLogueado.collectAsState().value 

    // Acceso a estado de los atributos de ReporteProblemaUiState desde el ViewModel
    val estadoReporteProblema by viewModel.reportarProblema.collectAsState()

	// Se recuerda a nivel de la clase el estado para el ExposedDropdownMenuBox
	var expandido by remember{ mutableStateOf(false) }

	// Estado del Snackbar
	val snackBarHostState = remember { SnackbarHostState() }
	val scope = rememberCoroutineScope()
	
	// Listado de tipo de problemas que tiene el usuario para elegir
	val tipoProblemas = listOf(
		"Carga de datos inventario",
		"Sincronización con servidor",
		"Rendimiento lento",
		"Agregar/Eliminar ítems",
		"Conectividad",
		"Otro"
	)

	// Listado de niveles de prioridad del problema
	val nivelesProblema = listOf("Alto", "Medio", "Bajo")

	// Variables para menu Drawer (Falta importar dependencias en header)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scopeDrawer = rememberCoroutineScope()

    // Implementacion de Drawer
    MainDrawer(navController, drawerState, scopeDrawer){
    	Scaffold(
    		//topBar = {MainTopBar(navController, drawerState, scopeDrawer, viewModel)},
    		topBar = {MainTopBar(navController, drawerState, scopeDrawer)},
    		snackbarHost = {SnackbarHost(snackBarHostState)},
    		modifier = Modifier.fillMaxSize()
    	){ innerPadding ->
    		Column(
    			modifier = Modifier
    				.padding(innerPadding)
    				.padding(16.dp),
    			horizontalAlignment = Alignment.CenterHorizontally
    		){
    			Text(
    				text = "Reportar Problema",
    				modifier = Modifier.padding(24.dp),
    	            fontWeight = FontWeight.Bold,
    	            textAlign = TextAlign.Center,
    	            style = MaterialTheme.typography.titleLarge
    			)
    
    			// Campos de textos para reportar problema
    			// Correo: Solo se mostrara, pero no se podra editar porque es el del usuario
    			OutlinedTextField(
    				value = estadoUsuario?.email ?: "", // Operador Elvis por si el valor es null, ya que 'value' espera solo un String
    				onValueChange = {},
    				label = {Text(text = "Correo")},
    				readOnly = true,
    				modifier = Modifier.fillMaxWidth()
    			)
    
    			Spacer(Modifier.size(7.dp))
    
    			// Menu para elegir el tipo de problema
    			ExposedDropdownMenuBox(
    				expanded = expandido,
    				onExpandedChange = {expandido = it}
    			){
    				OutlinedTextField(
    					value = estadoReporteProblema.tipoProblema,
    					onValueChange = {viewModel.onTipoProblemaChange(it)},
    					label = {Text(text = "Elige el tipo de problema")},
    					readOnly = true,
    					isError = estadoReporteProblema.tipoProblemaError != null,
    					supportingText = {
    						estadoReporteProblema.tipoProblemaError?.let{
    							Text(text = it, color = MaterialTheme.colorScheme.error)
    						}
    					},
    					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
    					modifier = Modifier
    						.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
    						.fillMaxWidth()
    				)
    
    				// Contenedor de los items del ExposedDropdownMenuBox
    				ExposedDropdownMenu(
    					expanded = expandido, // Valor inicial
    					onDismissRequest = {expandido = false} // Cuando se presione afuera del Menu, este se cierra
    				) {
    					// Opciones -> Loop a los valores del list 'tipoProblemas' para evitar codigo repetitivo
    					tipoProblemas.forEach{ tProblema ->
    						DropdownMenuItem(
    							text = {Text(text = tProblema)},
    							onClick = {
    								viewModel.actualizarTipoProblema(tProblema)
    								expandido = false
    							}
    						)
    					}
    				}
    			}
    
    			Spacer(Modifier.size(7.dp))
    
    			// Radio buttons para elegir nivel de prioridad del problema
    			// Basado en: https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#RadioButton(kotlin.Boolean,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Boolean,androidx.compose.material3.RadioButtonColors,androidx.compose.foundation.interaction.MutableInteractionSource)
    			Text(text = "Indique el nivel de prioridad del problema")
    
    			Column(modifier = Modifier.selectableGroup()) {
    				nivelesProblema.forEach { nivel ->
    					Row(
    						modifier = Modifier.fillMaxWidth()
    							.selectable(
    								selected = (nivel == estadoReporteProblema.nivelProblema),
    								onClick = {viewModel.actualizarNivelProblema(nivel)}
    							)
    							.padding(horizontal = 16.dp),
    						verticalAlignment = Alignment.CenterVertically
    					) {
    						RadioButton(
    							selected = (nivel == estadoReporteProblema.nivelProblema),
    							onClick = {viewModel.actualizarNivelProblema(nivel)}
    						)
    
    						Text(
    							text = nivel
    						)
    					}
    				}
    			}
    
    			// Mensaje de error si no se ha elegido el nivel de prioridad del problema
    			// Si hay contenido en 'nivelProblemaError', se muestra. Si es nulo, no aparece este Composable
    			estadoReporteProblema.nivelProblemaError?.let {
    				Text(
    					text = it,
    					color = MaterialTheme.colorScheme.error
    				)
    			}
    
    			Spacer(Modifier.size(7.dp))
    
    			// Campo para escribir detalle (No es un campo obligatorio, asi que no muestra un error)
    			OutlinedTextField(
    				value = estadoReporteProblema.detalleProblema,
    				onValueChange = {viewModel.onDetalleProblemaChange(it)},
    				label = {Text(text = "Escriba el detalle de su problema")},
    				modifier = Modifier.fillMaxWidth()
    			)
    
    			Spacer(Modifier.size(7.dp))
    
    			// Boton para enviar el reporte
    			Button(
    				onClick = {
    					val sePuedeEnviarReporte = viewModel.canSubmitReportar()
    
    					if(sePuedeEnviarReporte) {
    						
    						// Para mostrar SnackBar en una corrutina
    						scope.launch{
    							snackBarHostState.showSnackbar(
    								message = "Reporte enviado éxitosamente",
    								actionLabel = "Cerrar",
    								duration = SnackbarDuration.Short // Se mostrara por 4 segundos
    							)
    						}
    
    						// Limpieza de los campos, menos el de email
    						viewModel.actualizarTipoProblema("")
    						viewModel.actualizarNivelProblema("")
    						viewModel.onDetalleProblemaChange("")
    					}
    				},
    				modifier = Modifier.fillMaxWidth()
    			) {
    				Text(text = "Enviar reporte")
    			}
    		}
    	}
    }
}