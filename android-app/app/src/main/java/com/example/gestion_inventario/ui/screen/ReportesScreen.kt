@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.gestion_inventario.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gestion_inventario.ui.viewmodel.ReportarProblemaViewModel

@Composable
fun ReportesScreenVm(
    vm: ReportarProblemaViewModel,
    onSuccessNavigate: () -> Unit,
    onCancel: () -> Unit
){
    val state by vm.registrorepo.collectAsStateWithLifecycle()

    if (state.success){
        vm.clearRegistroResult()
        onSuccessNavigate()
    }

    ReportesScreen(
        nombrerep = state.nombrerep,
        emailrep = state.emailrep,
        problema = state.problema,
        descripcionrep = state.descripcionrep,

        errorNombrerep = state.errorNombrerep,
        errorEmailrep = state.errorEmailrep,



        isSubmitting = state.isSubmitting,
        errorMsg = state.errorMsg,

        onDescripcionrepChange = vm::onDescripcionrepChange,
        onNombrerepChange = vm::onNombrerepChange,
        onEmailrepChange = vm::onEmailrepChange,
        onProblemaChange = vm::onProblemaChange,
        onSubmit = vm::submitRegister,
        onCancel = onCancel
    )
}

@Composable
private fun ReportesScreen(
    nombrerep: String,
    emailrep: String,
    problema: String,
    descripcionrep: String,


    errorNombrerep: String?,
    errorEmailrep: String?,
    errorMsg: String?,
    isSubmitting: Boolean,

    onDescripcionrepChange: (String) -> Unit,
    onNombrerepChange: (String) -> Unit,
    onEmailrepChange: (String) -> Unit,
    onProblemaChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) {

    val bg = MaterialTheme.colorScheme.background

    var showPass by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Reportes",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(12.dp))

            // ---------- NOMBRE ----------
            CampoTextoRep("Nombre", nombrerep, onNombrerepChange, errorNombrerep)

            // ---------- EMAIL ----------
            CampoTextoRep("Correo electrónico", emailrep, onEmailrepChange, errorEmailrep)


            CampoTextoRep("Descripcion", descripcionrep,onDescripcionrepChange)

            Spacer(Modifier.height(8.dp))


            // ---------- TIPO DE USUARIO ----------
            var expanded by remember { mutableStateOf(false) }
            val opciones = listOf("Administrador", "Empleado")

            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = problema,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Selecciona tu problema") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    opciones.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                onProblemaChange(opcion)
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ---------- BOTÓN REGISTRAR ----------
            Button(
                onClick = onSubmit,
                enabled = !isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSubmitting) {
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Creando usuario...")
                } else {
                    Text("Registrar")
                }
            }

            if (errorMsg != null) {
                Spacer(Modifier.height(8.dp))
                Text(errorMsg, color = MaterialTheme.colorScheme.error)
            }

            Spacer(Modifier.height(12.dp))

            // ---------- BOTÓN CANCELAR ----------
            OutlinedButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                Text("Cancelar")
            }
        }
    }
}


// -------------------------------------------------------------
// 3️⃣ CampoTexto reutilizable para no repetir tanto código
// -------------------------------------------------------------
@Composable
fun CampoTextoRep(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    error: String? = null
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(etiqueta) },
        singleLine = true,
        isError = error != null,
        modifier = Modifier.fillMaxWidth()
    )
    if (error != null) {
        Text(error, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
    }
}
