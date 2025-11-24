package com.example.gestion_inventario.model

data class AgregarProductoUiState(
    //val codigo: String = "",
    val nombreProducto: String = "",
    val descripcion: String = "",
    val talla: String = "",
    val color: String = "",
    val idTalla: Int? = null, // Para almacenar el ID de la talla
    val idColor: Int? = null, // Para almacenar el ID del color
    val precio: String = "",
    val cantidad: String = "",

    // Manejo de errores
    //val errorCodigo: String? = null,
    val errorNombreProducto: String? = null,
    val errorDescripcion: String? = null,
    val errorTalla: String? = null,
    val errorColor: String? = null,
    val errorPrecio: String? = null,
    val errorCantidad: String? = null
)
