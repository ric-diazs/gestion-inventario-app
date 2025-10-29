package com.example.gestion_inventario.ui.screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import com.example.gestion_inventario.data.local.storage.UsuarioPreferencias
import com.example.gestion_inventario.navigation.Routes
import com.example.gestion_inventario.ui.components.SecundaryTopBar
import com.example.gestion_inventario.viewmodel.AuthViewModel

@Composable
fun PerfilUsuarioScreen(
	navController: NavController,
	viewModel: AuthViewModel
) {
	// Acceso desde el ViewModel al estado actual del usuario logueado exitosamente
    val estadoUsuario = viewModel.usuarioLogueado.collectAsState().value

    // Variables a usar
    val nombreCompleto: String = "${estadoUsuario?.nombre ?: ""} ${estadoUsuario?.apellidos ?: ""}"
    val email: String = estadoUsuario?.email ?: ""
	val tipoUsuario: String = estadoUsuario?.tipoUsuario ?: ""

	// Definicion de Data Store
	val context = LocalContext.current
    val userPrefs = remember { UsuarioPreferencias(context) }
    val isLoggedIn by userPrefs.isLoggedIn.collectAsState(false)

	// Estados para foto de perfil
    var photoUriString by rememberSaveable { mutableStateOf<String?>(null) }
    var pendingCaptureUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Launcher para tomar foto
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUriString = pendingCaptureUri?.toString()
            Toast.makeText(context, "Foto tomada correctamente", Toast.LENGTH_SHORT).show()
        } else {
            pendingCaptureUri = null
            Toast.makeText(context, "No se tomó ninguna foto", Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
    	topBar = {SecundaryTopBar(Routes.HomeAdmin, navController)},
        modifier = Modifier.fillMaxSize()
    ) {innerPadding ->
    	Column(
    		modifier = Modifier
    			.padding(innerPadding)
    			.fillMaxSize(),
    		horizontalAlignment = Alignment.CenterHorizontally
    	){
    	    // Mostrar aqui foto de perfil (?) -> Tener acceso aca a recurso nativo (camara o carpeta de imagenes)
    	    // Foto de perfil circular
            if (photoUriString.isNullOrEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(Uri.parse(photoUriString))
                        .crossfade(true)
                        .build(),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            // Boton para tomar o volver a tomar foto
            Button(onClick = {
                val file = createTempImageFile(context)
                val uri = getImageUriForFile(context, file)
                pendingCaptureUri = uri
                takePictureLauncher.launch(uri)
            }) {
                Text(if (photoUriString.isNullOrEmpty()) "Tomar Foto" else "Volver a Tomar Foto")
            }

             // Boton para eliminar foto
            if (!photoUriString.isNullOrEmpty()) {
                OutlinedButton(onClick = { showDialog = true }) {
                    Text("Eliminar Foto")
                }
            }

            // Dialogo de confirmación para eliminar la foto
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Confirmación") },
                    text = { Text("¿Desea eliminar la foto?") },
                    confirmButton = {
                        TextButton(onClick = {
                            photoUriString = null
                            showDialog = false
                            Toast.makeText(context, "Foto eliminada", Toast.LENGTH_SHORT).show()
                        }) {
                            Text("Aceptar")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
    	
    	    // Informacion del usuario de la sesion
    	    Text(
    	    	text = nombreCompleto,
    	    	fontWeight = FontWeight.Bold,
    	        textAlign = TextAlign.Center,
    	        style = MaterialTheme.typography.titleLarge
    	    )

    	    // Estos campos son solo para mostrar texto. No se pueden modificar aca.
    	    OutlinedTextField(
    			value = email,
    			onValueChange = {},
    			label = {Text(text = "Correo")},
    			readOnly = true,
    			modifier = Modifier.fillMaxWidth()
    	    )

    	    OutlinedTextField(
    			value = tipoUsuario,
    			onValueChange = {},
    			label = {Text(text = "Tipo de usuario")},
    			readOnly = true,
    			modifier = Modifier.fillMaxWidth()
    	    )
    	}
    }
}

// Funciones para crear foto como temporal y para obtener la URI que lleva a donde esta la foto de perfil
fun createTempImageFile(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = File(context.cacheDir, "images").apply {
        if (!exists()) mkdirs()
    }
    return File(storageDir, "IMG_${timeStamp}.jpg")
}

fun getImageUriForFile(context: Context, file: File): Uri {
    val authority = "${context.packageName}.fileprovider"
    return FileProvider.getUriForFile(context, authority, file)
}