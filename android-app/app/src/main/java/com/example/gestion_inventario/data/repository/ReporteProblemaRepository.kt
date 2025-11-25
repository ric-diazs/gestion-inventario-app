package com.example.gestion_inventario.data.repository

import com.example.gestion_inventario.data.remote.RetrofitInstance
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.NivelPrioridadAPI
import com.example.gestion_inventario.data.remote.model.ProductoAPI
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.ReporteProblemaAPI
import com.example.gestion_inventario.data.remote.model.TicketReporteAPI
import com.example.gestion_inventario.data.remote.model.TipoProblemaAPI
import retrofit2.Response

class ReporteProblemaRepository {

    suspend fun obtenerTipoProblemaAPI(): List<TipoProblemaAPI> = RetrofitInstance.api.obtenerTiposProblema()

    suspend fun obtenerNivelPrioridadAPI(): List<NivelPrioridadAPI> = RetrofitInstance.api.obtenerNivelPrioridad()

    suspend fun obtenerReporteProblemaAPI(): List<ReporteProblemaAPI> = RetrofitInstance.api.obtenerReportesProblema()

    suspend fun eliminarProblemaAPI(id: Int) {
        RetrofitInstance.api.eliminarReportes(id)
    }

    suspend fun obtenerReporteAPIPorId(id: Int): ReporteProblemaAPI = RetrofitInstance.api.obtenerReportePorId(id)

    suspend fun actualizarReporteAPI(id: Int, reporteActualizar: TicketReporteAPI) {
        RetrofitInstance.api.reporteActualizar(id, reporteActualizar)
    }


    suspend fun RegistrarProblemaAPI(
        correo: String,
        descripcion: String,
        tipoProblema: Int,
        nivelPrioridad: Int
    ){

        val ticket = TicketReporteAPI(
            correo = correo,
            descripcion = descripcion,
            tipoProblema = IdObject(tipoProblema),
            nivelPrioridad = IdObject(nivelPrioridad)
        )

       RetrofitInstance.api.guardarReporteProblema(ticket)
    }
}