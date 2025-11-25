package com.example.gestion_inventario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gestion_inventario.data.repository.ReporteProblemaRepository

class ReporteViewModelFactory (
    private val reporteProblemaRepository: ReporteProblemaRepository
): ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModelReporte::class.java)) {
            return AuthViewModelReporte(reporteProblemaRepository) as T
        }
        // Si se consulta por otra clase, se lanza error
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
