package com.example.gestion_inventario.validation

fun validateProductCode(codigo: String): String? {
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

fun validateName(nombre: String): String?{
    if(nombre.isBlank()) return "El nombre es obligatorio"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(nombre)) "Solo se aceptan letras y espacios" else null
}

//validaciones para el nombre:  no este vacio, solo letras

fun validateDescripcion(descrip: String): String?{
    if(descrip.isBlank()) return "La descripción es obligatoria"
    val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
    return if(!regex.matches(descrip)) "Solo se aceptan letras y espacios" else null
}

//validaciones para el talla
fun validateShoeSize(talla: String): String? {
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
fun validatePriceCLP(precio: String): String? {
    if (precio.isBlank()) return "El precio es obligatorio"

    // Solo números con puntos cada tres dígitos, ej: 10.000 o 1.000.000
    val regex = Regex("^[0-9]{1,3}(\\.[0-9]{3})*$")

    return if (!regex.matches(precio)) {
        "El precio debe tener el formato 10.000 (solo puntos como separadores de miles)"
    } else null
}

//Validaciones de cantidad

fun validateQuantity(cantidad: String): String? {
    if (cantidad.isBlank()) return "La cantidad es obligatoria"

    val regex = Regex("^[0-9]+$") // Solo números enteros positivos

    return if (!regex.matches(cantidad)) {
        "La cantidad solo puede contener números enteros"
    } else null
}