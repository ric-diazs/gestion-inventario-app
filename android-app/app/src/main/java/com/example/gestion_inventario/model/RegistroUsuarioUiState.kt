package com.example.gestion_inventario.model

data class RegistroUsuarioUiState (

    val nombre: String = "",                              // 2) Nombre
    val apellido: String = "",                            // 3) Apellido
    val email: String = "",                               // 4) Email
    val contrasena: String = "",                          // 5) Contraseña
    val confirmar: String = "",                           // 6) Confirmar contraseña
    val tipoUsuario: String = "",                         // 7) Tipo de usuario

    // Errores por campo
    val errorNombre: String? = null,
    val errorApellido: String? = null,
    val errorEmail: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmar: String? = null,
    val errorTipoUsuario: String? = null
)

