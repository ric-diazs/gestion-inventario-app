package com.example.gestion_inventario

import com.example.gestion_inventario.data.local.dao.ProductoDao
import com.example.gestion_inventario.data.remote.ApiService
import com.example.gestion_inventario.data.remote.model.ProductoAPI
import com.example.gestion_inventario.data.repository.ProductoRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

// Se crea una subclase de ProductoRepository para inyectar manualmente el ApiService
class TestableProductoRepository(
	productoDao: ProductoDao,
	private val testApi: ApiService
): ProductoRepository(productoDao){
	override suspend fun obtenerProductosAPI(): List<ProductoAPI>{
		return testApi.obtenerProductos()
	}
}

class ProductoRepositoryTest : StringSpec({
	"obtenerProductosAPI() debe devolver una lista de productos simulada"{		
		// 1. Se simula la respuesta de la API
		val productosFalsos = listOf(
			ProductoAPI(1, "Nike Shock", "Descripcion 1", 65000, 30, "2025-11-23"),
			ProductoAPI(2, "Adidas Original", "Descripcion 2", 80000, 55, "2025-11-25")
		)

		// 2. Se crea un mock del ApiService
		val mockApi = mockk<ApiService>()
		coEvery{ mockApi.obtenerProductos() } returns productosFalsos

		// 3. Se utiliza la clase de test para inyectar el mock
		val mockDao = mockk<ProductoDao>(relaxed = true) // Se crea este mock del DAO para poder inyectarlo a la clase de test
		val repo = TestableProductoRepository(mockDao, mockApi)

		// 4. Se ejecuta el test
		runTest{
			val resultado = repo.obtenerProductosAPI()
			resultado shouldContainExactly productosFalsos
		}
	}
})