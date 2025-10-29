package com.example.gestion_inventario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.data.repository.ProductoRepository

// Sobre Factory e Inyeccion de dependencias, revisar aca:
// https://developer.android.com/topic/libraries/architecture/viewmodel/viewmodel-factories
class AuthViewModelFactory(
    private val usuarioRepository: UsuarioRepository,
    private val productoRepository: ProductoRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Cuando se solicite el ViewModel, ahora se entrega con el Repository.
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(usuarioRepository, productoRepository) as T
        }
        // Si se consulta por otra clase, se lanza error
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
