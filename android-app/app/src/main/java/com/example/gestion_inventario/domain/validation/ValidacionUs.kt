package com.example.gestion_inventario.domain.validation

import android.util.Patterns

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
fun validateConfirm(contrasena: String, confirmar: String): String? {
    return if (contrasena != confirmar) "Las contraseñas no coinciden" else null
}






//---------------------------------------VALIDACIONES PARA PANTALLA DE AGREGAR PRODUCTO-----------------------------------------------------------------------
fun validateCodigo(codigo: String): String? {
    if (codigo.isBlank()) {
        return "El código es obligatorio"
    }

    // Solo letras y números
    val regex = Regex("^[A-Za-z0-9]+$")

    return if (!regex.matches(codigo)) {
        "El código solo puede contener letras y números"
    } else {
        null // No hay error
    }
}

//validaciones para el nombre:  no este vacio, solo letras

fun validateNombrepro(nombrepro: String): String?{
    if(nombrepro.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombrepro)) "Solo se aceptan letras y espacios" else null
}

//validaciones para el nombre:  no este vacio, solo letras

fun validateDescripcion(descripcion: String): String?{
    if(descripcion.isBlank()) return "La descripción es obligatoria"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(descripcion)) "Solo se aceptan letras y espacios" else null
}

//validaciones para el talla
fun validateTalla(talla: String): String? {
    if (talla.isBlank()) return "La talla es obligatoria"

    val regex = Regex("^[0-9]+(\\.[0-9]+)?$") // Acepta enteros o decimales

    return if (!regex.matches(talla)) {
        "La talla solo puede contener números (ej: 38 o 38.5)"
    } else null
}

fun validateColor(color: String): String?{
    if(color.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(color)) "Solo se aceptan letras y espacios" else null
}

//Validacion para precio
fun validatePrecioClp(precio: String): String? {
    if (precio.isBlank()) return "El precio es obligatorio"

    // Solo números con puntos cada tres dígitos, ej: 10.000 o 1.000.000
    val regex = Regex("^[0-9]{1,3}(\\.[0-9]{3})*$")

    return if (!regex.matches(precio)) {
        "El precio debe tener el formato 10.000 (solo puntos como separadores de miles)"
    } else null
}

//Validaciones de cantidad

fun validateCant(cantidad: String): String? {
    if (cantidad.isBlank()) return "La cantidad es obligatoria"

    val regex = Regex("^[0-9]+$") // Solo números enteros positivos

    return if (!regex.matches(cantidad)) {
        "La cantidad solo puede contener números enteros"
    } else null
}



//----------------------------------------------------------------VALIDACIONES PARA REPORTAR PROBLEMA------------------------------------------------------------------


fun validateEmailrep(email: String): String?{
    if(email.isBlank()) return "El correo es obligatorio"
    val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    return if(!ok) "Formato de correo Inválido" else null }

fun validateNombre3(nombre: String): String?{
    if(nombre.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombre)) "Solo se aceptan letras y espacios" else null
}