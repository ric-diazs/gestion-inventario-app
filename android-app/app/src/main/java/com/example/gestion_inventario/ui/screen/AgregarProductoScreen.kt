@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.gestion_inventario.ui.screen

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.gestion_inventario.ui.viewmodel.ProductoAdminViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AgregarProductoScreenVm(
    vm: ProductoAdminViewModel,
    onSuccessNavigate: () -> Unit,
    onCancel: () -> Unit
){
    val state by vm.registropro.collectAsStateWithLifecycle()

    if (state.success){
        vm.clearRegistroResult()
        onSuccessNavigate ()
    }
    AgregarProductoScreen(
        codigo = state.codigo,
        nombrepro = state.nombrepro,
        descripcion= state.descripcion,
        talla = state.talla,
        color = state.color,
        precio = state.precio,
        cantidad = state.cantidad,

        errorCodigo = state.errorCodigo,
        errorNombrepro = state.errorNombrepro,
        errorDescripcion = state.errorDescripcion,
        errorTalla = state.errorTalla,
        errorColor = state.errorColor,
        errorPrecio = state.errorPrecio,
        errorCantidad = state.errorCantidad,

        isSubmitting = state.isSubmitting,
        errorMsg = state.errorMsg,

        onCodigoChange = vm::onCodigoChange,
        onNombreproChange = vm::onNombreproChange,
        onDescripcionChange = vm::onDescripcionChange,
        onTallaChange = vm::onTallaChange,
        onColorChange = vm::onColorChange,
        onPrecioChange = vm::onPrecioChange,
        onCantidadChange = vm::onCantidadChange,
        onSubmit = vm::submitRegister,
        onCancel = onCancel


    )

}


@Composable

private fun AgregarProductoScreen(
    codigo: String,
    nombrepro: String,
    descripcion: String,
    talla: String,
    color: String,
    precio: String,
    cantidad: String,

    errorCodigo: String?,
    errorNombrepro: String?,
    errorDescripcion: String?,
    errorTalla: String?,
    errorColor: String?,
    errorPrecio: String?,
    errorCantidad: String?,
    isSubmitting: Boolean,
    errorMsg: String?,

    onCodigoChange: (String) -> Unit,
    onNombreproChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onTallaChange: (String) -> Unit,
    onColorChange: (String) -> Unit,
    onPrecioChange: (String) -> Unit,
    onCantidadChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onCancel: () -> Unit

){
    val bg = MaterialTheme.colorScheme.background

    var showPass by remember { mutableStateOf(false) }
    var showConfirm by remember {mutableStateOf(false)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bg)
            .padding(16.dp),
        contentAlignment = Alignment.Center

    ) {
        Column(modifier = Modifier.fillMaxWidth()){
            Text(
                text = "Registrar Producto",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(12.dp))

            // ---------- CODIGO ----------
            CampoTextos("Codigo", codigo, onCodigoChange, errorCodigo)
            // ---------- NOMBRE ----------
            CampoTextos("Nombre de Producto", nombrepro, onNombreproChange, errorNombrepro)

            // ---------- DESCRIPCION ----------
            CampoTextos("Descripcion", descripcion, onDescripcionChange, errorDescripcion)

            // ---------- TALLA ----------
            CampoTextos("Talla", talla, onTallaChange, errorTalla)

            // ---------- COLOR ----------
            CampoTextos("Color", color, onColorChange, errorColor)

            // ---------- PRECIO ----------
            CampoTextos("Precio", precio, onPrecioChange, errorPrecio)

            // ---------- CANTIDAD ----------
            CampoTextos("Cantidad", cantidad, onCantidadChange, errorCantidad)


            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onSubmit,
                enabled = !isSubmitting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSubmitting){
                    CircularProgressIndicator(strokeWidth = 2.dp, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Agregando Producto")

                } else {
                    Text("Agregar")

                }

                if ( errorMsg != null){
                    Spacer(Modifier.height(8.dp))
                    Text(errorMsg, color = MaterialTheme.colorScheme.error)

                }

                Spacer(Modifier.height(12.dp))

                OutlinedButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancelar")
                }
            }

        }
    }
}

@Composable
fun CampoTextos(
    etiqueta: String,
    valor: String,
    onValorChange: (String) -> Unit,
    error: String?
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
