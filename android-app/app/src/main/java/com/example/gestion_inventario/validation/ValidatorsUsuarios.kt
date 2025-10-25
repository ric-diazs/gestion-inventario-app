package com.example.gestion_inventario.validation
import android.util.Patterns

fun validateNameUs(nombreus: String): String?{
    if(nombreus.isBlank()) return "El nombre de usuario es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombreus)) "Solo se aceptan letras y espacios" else null
}

fun validateName2(nombre: String): String?{
    if(nombre.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombre)) "Solo se aceptan letras y espacios" else null
}

fun validateLastName(apellido: String): String?{
    if(apellido.isBlank()) return "El Apellido es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(apellido)) "Solo se aceptan letras y espacios" else null
}

fun validateEmail(email: String): String?{
    if(email.isBlank()) return "El correo es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if(!ok) "Formato de correo Inválido" else null }

fun validateContra(contrasena: String): String? {
    if (contrasena.isBlank()) return "La contraseña es obligatoria"

    // Mínimo 12 caracteres, una mayúscula, una minúscula y un número
    val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{12,}$")

    return if (!regex.matches(contrasena)) {
        "Debe tener al menos 12 caracteres, una mayúscula, una minúscula y un número"
    } else null
}