package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.local.dao.RegistroDao
import com.example.gestion_inventario.data.local.dao.ReporteDao
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import com.example.gestion_inventario.data.local.entity.RegistroEntity
import com.example.gestion_inventario.data.local.entity.ReporteEntity


class RegistroRepository(
    private val registroDao: RegistroDao
) {

    // ---- REGISTRO DE USUARIO ----
    suspend fun register(
        nombre: String,
        apellido: String,
        email: String,
        contrasena: String,
        tipoUsuario: String
    ): Result<Long> {
        // Verificar si el correo ya existe
        val exists = registroDao.obtenerRegistro(email) != null
        if (exists) {
            return Result.failure(IllegalStateException("El correo ya está registrado"))
        }

        // Crear usuario nuevo
        val id = registroDao.insert(
            RegistroEntity(
                nombre = nombre,
                apellido = apellido,
                email = email,
                contrasena = contrasena,
                confirmar = contrasena,
                tipoUsuario = tipoUsuario
            )
        )

        return Result.success(id)
    }
}


class ProductoRepository(
    private val productoDao: ProductoDao                              // Inyección del DAO
) {

    // ---- REGISTRO DE PRODUCTO ----
    suspend fun registerpro(
        codigo: String,
        nombrepro: String,
        descripcion: String,
        talla: String,
        color: String,
        precio: String,
        cantidad:String

    ): Result<Long> {
        // Verificar si el El CODIGO PRODUCTO YA EXISTE
        val exists = productoDao.getByCodigo(codigo) != null
        if (exists) {
            return Result.failure(IllegalStateException("El Codigo  ya está registrado"))
        }

        // Crear usuario nuevo
        val id = productoDao.insert(
            ProductoEntity(
                codigo = codigo,
                nombrepro = nombrepro,
                descripcion = descripcion,
                talla = talla,
                color = color,
                precio = precio,
                cantidad = cantidad
            )
        )

        return Result.success(id)
    }
}

class ReporteRepository(
    private val reporteDao: ReporteDao                              // Inyección del DAO
) {

    // ---- REGISTRO DE PRODUCTO ----
    suspend fun registerepo(
        nombrerep: String,
        emailrep: String,
        descripcionrep: String


    ): Result<Long> {

        // Crear usuario nuevo
        val id = reporteDao.insert(
            ReporteEntity(

                nombrerep = nombrerep,
                emailrep = emailrep,
                descripcionrep = descripcionrep
            )
        )

        return Result.success(id)
    }
}