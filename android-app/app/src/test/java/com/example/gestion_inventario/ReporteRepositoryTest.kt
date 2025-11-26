package com.example.gestion_inventario

import com.example.gestion_inventario.data.remote.ApiService
import com.example.gestion_inventario.data.remote.model.NivelPrioridadAPI
import com.example.gestion_inventario.data.remote.model.ReporteProblemaAPI
import com.example.gestion_inventario.data.remote.model.TipoProblemaAPI
import com.example.gestion_inventario.data.repository.ReporteProblemaRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest



class TestableReporteRepository(
    private val testApi: ApiService
): ReporteProblemaRepository(){

    override suspend fun obtenerReportesAPI(): List<ReporteProblemaAPI>{
        return testApi.obtenerReportesProblema()
    }
}

class ReporteRepositoryTest : StringSpec({
    "obtenerReportesAPI() debe devolver una lista reportes simulados"{
        // 1. Se simula la respuesta de la API
        val reportesFalsos = listOf(
            ReporteProblemaAPI(
                id = 1,
                correo = "juan.perez@empresa.com",
                descripcion = "Descripcion 1",
                tipoProblema = TipoProblemaAPI(
                    id = 1,
                    tipoProblema = "Carga de datos inventario",
                    reportesProblema = emptyList()
                ),
                nivelPrioridad = NivelPrioridadAPI(
                    id = 2,
                    nivelPrioridad = "Medio",
                    reportesProblema = emptyList()
                )
            ),

            ReporteProblemaAPI(
                id = 2,
                correo = "maria.gomez@inventarios.cl",
                descripcion = "Descripcion 2",
                tipoProblema = TipoProblemaAPI(
                    id = 2,
                    tipoProblema = "Sincronización con servidor",
                    reportesProblema = emptyList()
                ),
                nivelPrioridad = NivelPrioridadAPI(
                    id = 1,
                    nivelPrioridad = "Alto",
                    reportesProblema = emptyList()
                )
            )
        )

        val mockApi = mockk<ApiService>()
        coEvery { mockApi.obtenerReportesProblema() } returns reportesFalsos

        val repo = TestableReporteRepository(mockApi)

        runTest {
            val result = repo.obtenerReportesAPI()
            result shouldContainExactly reportesFalsos
        }


    }
})