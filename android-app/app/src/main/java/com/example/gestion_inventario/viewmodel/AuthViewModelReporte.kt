package com.example.gestion_inventario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_inventario.data.remote.model.NivelPrioridadAPI
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.ReporteProblemaAPI
import com.example.gestion_inventario.data.remote.model.TicketReporteAPI
import com.example.gestion_inventario.data.remote.model.TipoProblemaAPI
import com.example.gestion_inventario.data.remote.model.TipoUsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioAPI
import com.example.gestion_inventario.data.repository.ReporteProblemaRepository
import com.example.gestion_inventario.model.AgregarProductoUiState
import com.example.gestion_inventario.model.LoginUiState
import com.example.gestion_inventario.model.ReportarProblemaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModelReporte ( private val reporteProblemaRepository: ReporteProblemaRepository
): ViewModel(){
    private val _reportarProblema = MutableStateFlow(value = ReportarProblemaUiState())
    private val _reporteProblema = MutableStateFlow<List<ReporteProblemaAPI>>(emptyList())
    private val _tipoProblemas = MutableStateFlow<List<TipoProblemaAPI>>(emptyList())

    private val _reporteApi = MutableStateFlow<ReporteProblemaAPI?>(null)

    private val _nivelPrioridad = MutableStateFlow<List<NivelPrioridadAPI>>(emptyList())


    val reporteApi : StateFlow<ReporteProblemaAPI?> = _reporteApi

    val reportarProblema: StateFlow<ReportarProblemaUiState> = _reportarProblema

    val tiposproblema : StateFlow<List<TipoProblemaAPI>> = _tipoProblemas

    val nivelPrioridad : StateFlow<List<NivelPrioridadAPI>> = _nivelPrioridad

    val reporteProblema : StateFlow<List<ReporteProblemaAPI>> = _reporteProblema


    init {
        cargarTipoProblemaApi()
    }

    init {
        cargarNivelPrioridadApi()
    }

    init {
        cargarReporteProblemaApi()
    }

    fun limpiarProblemas() {
        _reportarProblema.value = _reportarProblema.value.copy(
            tipoProblema = "",
            nivelProblema = "",
            idTipoProblema = null,
            idNivelProblema = null,
            tipoProblemaError = null,
            nivelProblemaError = null
        )
    }

    fun estaVacio(campo: String?): Boolean {
        if(campo.isNullOrBlank()) return true else return false
    }
    fun validarTipoProblema(tipoProblema: String): String? {
        if(estaVacio(tipoProblema)) return "Debes elegir el tipo de problema" else return null
    }

    fun validarNivelProblema(nivelProblema: String): String? {
        if(estaVacio(nivelProblema)) return "Debes elegir un nivel de problema" else return null
    }

    /*==== Funciones para gestionar estados de campos de reporte de problema. ====*/
    fun onTipoProblemaChange(valorTipoProblema: String) {
        _reportarProblema.update{ it.copy(tipoProblema = valorTipoProblema, tipoProblemaError = validarTipoProblema(valorTipoProblema)) }
    }

    fun onNivelProblemaChange(valorNivelProblema: String) {
        _reportarProblema.update{ it.copy(nivelProblema = valorNivelProblema, nivelProblemaError = validarNivelProblema(valorNivelProblema)) }
    }

    fun onDetalleProblemaChange(valorDetalleProblema: String) {
        _reportarProblema.update{ it.copy(detalleProblema = valorDetalleProblema) }
    }

    // Funcion para actualizar estado del tipo de problema
    fun actualizarTipoProblema(valorTipoProblema: String, valorIdTipoProblema: Int) {
        _reportarProblema.update{ it.copy(tipoProblema = valorTipoProblema, idTipoProblema = valorIdTipoProblema, tipoProblemaError = null) }
    }

    // Funcion para actualizar estado de nivel de prioridad del problema
    // Es como el para el 'onValueChange', pero para los radio buttons de esta parte de la vista
    fun actualizarNivelProblema(valorNivelProblema: String, valorIdNivelProblema: Int) {
        _reportarProblema.update{ it.copy(nivelProblema = valorNivelProblema, idNivelProblema = valorIdNivelProblema, nivelProblemaError = null) }
    }

    fun submitActualizarReporteAPI(id: Int, reporteActualizar : TicketReporteAPI) {
        viewModelScope.launch {
            reporteProblemaRepository.actualizarReporteAPI(id, reporteActualizar)
        }
    }

    // Funcion para permitir envio de reporte
    fun canSubmitReportar():Boolean {
        val estadoReportarProblema = _reportarProblema.value

        val msgErrorUpdatedTProblema = validarTipoProblema(estadoReportarProblema.tipoProblema)
        val msgErrorUpdatedNProblema = validarNivelProblema(estadoReportarProblema.nivelProblema)

        _reportarProblema.update{ it.copy(tipoProblemaError = msgErrorUpdatedTProblema, nivelProblemaError = msgErrorUpdatedNProblema) }

        if(msgErrorUpdatedTProblema != null || msgErrorUpdatedNProblema != null) return false

        return true
    }


    fun cargarTipoProblemaApi() {
        viewModelScope.launch {
            try {
                _tipoProblemas.value = reporteProblemaRepository.obtenerTipoProblemaAPI()
            } catch(e: Exception) {
                println("Error el tipo de problema: ${e.localizedMessage}")
            }
        }
    }

    fun cargarNivelPrioridadApi() {
        viewModelScope.launch {
            try {
                _nivelPrioridad.value = reporteProblemaRepository.obtenerNivelPrioridadAPI()
            } catch(e: Exception) {
                println("Error el tipo de problema: ${e.localizedMessage}")
            }
        }
    }

    fun cargarReporteProblemaApi() {
        viewModelScope.launch {
            try {
                _reporteProblema.value = reporteProblemaRepository.obtenerReporteProblemaAPI()
            } catch(e: Exception) {
                println("Error el tipo de problema: ${e.localizedMessage}")
            }
        }
    }

    fun cargarReporteApiPorId(id: Int){
        viewModelScope.launch{
            try{
                _reporteApi.value = reporteProblemaRepository.obtenerReporteAPIPorId(id)
            } catch(e: Exception) {
                println("Error al obtener el reporte de id ${id}: ${e.localizedMessage}")
            }
        }
    }

    // Funcion para registrar producto en API (Metodo POST).
    fun submitRegistrarProblemaAPI() {
        val estadoReporteProApi = _reportarProblema.value

        viewModelScope.launch {
            reporteProblemaRepository.RegistrarProblemaAPI(
                correo = estadoReporteProApi.email,
                descripcion = estadoReporteProApi.detalleProblema,
            tipoProblema = estadoReporteProApi.idTipoProblema!!,
            nivelPrioridad = estadoReporteProApi.idNivelProblema!!
            )
        }
    }

    fun obtenerTipoProblema(problemaId: Int) : TipoProblemaAPI? {
        return tiposproblema.value.firstOrNull { tProblemaApi ->
            tProblemaApi.reportesProblema.any {it.id == problemaId}
        }
    }
    fun obtenerNivelPrioridad (problemaId: Int) : NivelPrioridadAPI? {
        return nivelPrioridad.value.firstOrNull { nProblemaApi ->
            nProblemaApi.reportesProblema.any {it.id == problemaId}
        }
    }

    fun submitEliminarProblemaAPI(id: Int) {
        viewModelScope.launch {
            reporteProblemaRepository.eliminarProblemaAPI(id)
        }
    }


}