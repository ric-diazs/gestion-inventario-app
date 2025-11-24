package com.example.gestion_inventario.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.gestion_inventario.data.remote.model.ColorAPI
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.ProductoAPI
import com.example.gestion_inventario.data.remote.model.ProductoSolicitud
import com.example.gestion_inventario.data.remote.model.TallaAPI
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.model.AgregarProductoUiState

class ProductoViewModel(
	private val productoRepository: ProductoRepository
) : ViewModel() {
	// Manejo de estado de formulario de Agregar Producto
	private val _registrarProducto = MutableStateFlow(value = AgregarProductoUiState())
	private val _producto = MutableStateFlow<List<ProductoEntity>>(emptyList())
	private val _productoSeleccionado = MutableStateFlow<ProductoEntity?>(null)
	private val _colores = MutableStateFlow<List<ColorAPI>>(emptyList())
	private val _idObject = MutableStateFlow<IdObject?>(null)
    private val _tallas = MutableStateFlow<List<TallaAPI>>(emptyList())
	private val _productosAPI = MutableStateFlow<List<ProductoAPI>>(emptyList())
    private val _productoAPI = MutableStateFlow<ProductoAPI?>(null)
    private val _productoSolicitudAPI = MutableStateFlow<ProductoSolicitud?>(null)
	
	// Variable publica para acceder a estado de atributos de agregar producto desde otra clase
	val registrarProducto: StateFlow<AgregarProductoUiState> = _registrarProducto
	val productos: StateFlow<List<ProductoEntity>> = _producto
	val productoSeleccionado: StateFlow<ProductoEntity?> = _productoSeleccionado
	val colores : StateFlow<List<ColorAPI>> = _colores
	val idObject : StateFlow<IdObject?> = _idObject
	val tallas : StateFlow<List<TallaAPI>> = _tallas
    val productosAPI: StateFlow<List<ProductoAPI>> = _productosAPI
    val productoAPI: StateFlow<ProductoAPI?> = _productoAPI
    val productoSolicitudAPI: StateFlow<ProductoSolicitud?> = _productoSolicitudAPI

    /*
	=====================================
	Llamado automatico de funciones a API
	=====================================
	*/
	init {
		cargarColoresApi()
		cargarTallasApi()
		cargarProductosApi()
	}

	/*== Funciones para validar campos de formulario Agregar Producto == */
	// Validar si campo esta vacio
	fun estaVacio(campo: String?): Boolean {
		if(campo.isNullOrBlank()) return true else return false
	}
	
	// Funcion para validar el codigo
	fun validarCodigo(codigo: String): String? {
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

	//Funciones para validar los campos de productos que no deben estar vacios
	fun validarNombreProducto(valorNombreProd: String): String?{
		if(estaVacio(valorNombreProd)) return "El nombre es obligatorio"
		val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		return if(!regex.matches(valorNombreProd)) "Solo se aceptan letras y espacios" else null
	}

	fun validarDescripcion(descripcion: String): String?{
		if(estaVacio(descripcion)) return "La descripción es obligatoria" else return null
		//val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		//return if(!regex.matches(descripcion)) "Solo se aceptan letras y espacios" else null
	}

	fun validarTalla(talla: String): String? {
		if (estaVacio(talla)) return "La talla es obligatoria" else return null

		//val regex = Regex("^[0-9]+(\\.[0-9]+)?$") // Acepta enteros o decimales

		//return if (!regex.matches(talla)) {
			"La talla solo puede contener números (ej: 38 o 38.5)"
		//} else null
	}

	fun validarColor(color: String): String?{
		if(estaVacio(color)) return "El nombre es obligatorio" else return null
		//val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		//return if(!regex.matches(color)) "Solo se aceptan letras y espacios" else null
	}

	//Validaciones numericas
	fun validarPrecio(precio: String): String? {
		if (estaVacio(precio)) return "El precio es obligatorio"

		// Solo números con puntos cada tres dígitos, ej: 10.000 o 1.000.000
		//val regex = Regex("^[0-9]{1,3}(\\.[0-9]{3})*$")
		// Solo números enteros positivos
		val regex = Regex("^[0-9]+$") 

		return if (!regex.matches(precio)) {
			//"El precio debe tener el formato 10.000 (solo puntos como separadores de miles)"
			"El precio solo puede contener números enteros"
		} else null
	}

	fun validarCantidad(cantidad: String): String? {
		if (estaVacio(cantidad)) return "La cantidad es obligatoria"

		val regex = Regex("^[0-9]+$") // Solo números enteros positivos

		return if (!regex.matches(cantidad)) {
			"La cantidad solo puede contener números enteros"
		} else null
	}



	/*fun onCodigoChange(valorCodigo: String) {
		//val filtered = value.filter { it.isLetter() || it.isWhitespace() }
		_registrarProducto.update { it.copy(codigo = valorCodigo, errorCodigo = validarCodigo(valorCodigo)) }
	}*/

	fun onNombreProductoChange(valorNombreProd: String) {
		//val filtered = value.filter { it.isLetter() || it.isWhitespace() }
		_registrarProducto.update { it.copy(nombreProducto = valorNombreProd, errorNombreProducto = validarNombreProducto(valorNombreProd)) }
	}

	fun onDescripcionChange(valorDescripcion: String) {
		_registrarProducto.update { it.copy(descripcion = valorDescripcion, errorDescripcion = validarDescripcion(valorDescripcion)) }
	}

	fun onTallaChange(valorTalla: String) {
		_registrarProducto.update { it.copy(talla = valorTalla, errorTalla = validarTalla(valorTalla)) }
	}

	fun onColorChange(valorColor: String) {
		_registrarProducto.update { it.copy(color = valorColor, errorColor = validarColor(valorColor)) }
	}

	fun onPrecioChange(valorPrecio: String) {
		_registrarProducto.update { it.copy(precio = valorPrecio, errorPrecio = validarPrecio(valorPrecio)) }
	}

	fun onCantidadChange(valorCantidad: String) {
		_registrarProducto.update { it.copy(cantidad = valorCantidad, errorCantidad = validarCantidad(valorCantidad)) }
	}

	// Funcion para actualizar el estado del color (en campo dropdown)
	fun actualizarColor(valorColor: String, valorIdColor: Int) {
		_registrarProducto.update { it.copy(color = valorColor, idColor = valorIdColor, errorColor = null) }
	}

    // Funcion para actualizar el estado del color (en campo dropdown)
    fun actualizarTalla(valorTalla: String, valorIdTalla: Int) {
        _registrarProducto.update { it.copy(talla = valorTalla, idTalla = valorIdTalla, errorTalla = null) }
    }

	// Funcion para limpiar campos de registro de productos
	fun limpiarCamposRegistroProd() {
		_registrarProducto.value = AgregarProductoUiState()
	}

	/*
	======================================================
	    	Funciones que interactuan con BBDD local (Room)
	======================================================
	*/
	// Cargar los usuarios desde el repositorio
	fun cargarProductos() {
		viewModelScope.launch {
			productoRepository.obtenerProductos().collect { lista ->
				_producto.value = lista
			}
		}
	}

	fun cargarProductoPorId(id: Long){
		viewModelScope.launch {
			_productoSeleccionado.value = productoRepository.obtenerProductoPorId(id)
		}
	}

	fun eliminarProducto(id: Long) {
		viewModelScope.launch {
			productoRepository.eliminarProducto(id)
		}
	}


	fun actualizarProducto(producto: ProductoEntity, onSuccess: () -> Unit) {
		viewModelScope.launch {
			productoRepository.actualizarProducto(producto)
			onSuccess()
		}
	}


	// Funcion para validar si se puede registrar el producto
	fun canSubmitRegistrarProd():Boolean {
		val estadoRegistrarProd = _registrarProducto.value

		//val errorCodigoUpdated = validarCodigo(estadoRegistrarProd.codigo)
		val errorNombreProductoUpdated = validarNombreProducto(estadoRegistrarProd.nombreProducto)
		val errorDescripcionUpdated = validarDescripcion(estadoRegistrarProd.descripcion)
		val errorTallaUpdated = validarTalla(estadoRegistrarProd.talla)
		val errorColorUpdated = validarColor(estadoRegistrarProd.color)
		val errorPrecioUpdated = validarPrecio(estadoRegistrarProd.precio)
		val errorCantidadUpdated = validarCantidad(estadoRegistrarProd.cantidad)

		_registrarProducto.update{
			it.copy(
				//errorCodigo = errorCodigoUpdated,
				errorNombreProducto = errorNombreProductoUpdated,
				errorDescripcion = errorDescripcionUpdated,
				errorTalla = errorTallaUpdated,
				errorColor = errorColorUpdated,
				errorPrecio = errorPrecioUpdated,
				errorCantidad = errorCantidadUpdated
			)
		}

		if(
			//errorCodigoUpdated != null ||
			errorNombreProductoUpdated != null ||
			errorDescripcionUpdated != null ||
			errorTallaUpdated != null ||
			errorColorUpdated != null ||
			errorPrecioUpdated != null ||
			errorCantidadUpdated != null
		) {
			return false
		}

		return true
	}

	// Funcion para registrar producto
	/*fun submitRegistroProducto() {
		val estadoRegistrarProd = _registrarProducto.value

		// Se lanza corrutina por funcion de registro de Repository (es asincrona)
		viewModelScope.launch {
			productoRepository.registrarProducto(
				codigo = estadoRegistrarProd.codigo,
				nombreProducto = estadoRegistrarProd.nombreProducto,
				descripcion = estadoRegistrarProd.descripcion,
				talla = estadoRegistrarProd.talla,
				color = estadoRegistrarProd.color,
				precio = estadoRegistrarProd.precio,
				cantidad = estadoRegistrarProd.cantidad
			)
		}
	}*/


	/*
	=======================================================
	        Funciones que interactuan con API (Retrofit)
	=======================================================
	*/
	// Colores del producto
	fun cargarColoresApi() {
		viewModelScope.launch {
			try {
				_colores.value = productoRepository.obtenerColoresAPI()
			} catch(e: Exception) {
				println("Error al obtener colores: ${e.localizedMessage}")
			}
		}
	}

    // Tallas del producto
    fun cargarTallasApi() {
        viewModelScope.launch {
            try {
                _tallas.value = productoRepository.obtenerTallasAPI()
            } catch(e: Exception) {
                println("Error al obtener tallas: ${e.localizedMessage}")
            }
        }
    }

    // Listado de productos
    fun cargarProductosApi() {
        viewModelScope.launch {
            try {
                _productosAPI.value = productoRepository.obtenerProductosAPI()
            } catch(e: Exception) {
                println("Error al obtener productos: ${e.localizedMessage}")
            }
        }
    }

    // Listado de productos
    fun cargarProductoApiPorId(id: Int) {
        viewModelScope.launch {
            try {
                _productoAPI.value = productoRepository.obtenerProductoAPIPorId(id)
            } catch(e: Exception) {
                println("Error al obtener el producto de id ${id}: ${e.localizedMessage}")
            }
        }
    }

    // Funcion para registrar producto en API (Metodo POST).
	fun submitRegistroProductoAPI() {
		val estadoRegistroProdApi = _registrarProducto.value

		viewModelScope.launch {
			productoRepository.registrarProductoAPI(
				nombre = estadoRegistroProdApi.nombreProducto,
        		descripcion = estadoRegistroProdApi.descripcion,
        		idTalla = estadoRegistroProdApi.idTalla!!,
        		idColor = estadoRegistroProdApi.idColor!!,
        		precio = estadoRegistroProdApi.precio.replace(".", "").toInt(),
        		cantidad = estadoRegistroProdApi.cantidad.toInt()
			)
		}
	}

	// Funcion para actualizar producto (Metodo PUT)
	fun submitActualizarProductoAPI(id: Int, productoActualizar : ProductoSolicitud) {
		viewModelScope.launch {
			productoRepository.actualizarProductoAPI(id, productoActualizar)
		}
	}

	// Funcion para eliminar productos (Metodo DELETE)
	fun submitEliminarProductoAPI(id: Int) {
		viewModelScope.launch {
			productoRepository.eliminarProductoAPI(id)
		}
	}

	// Funciones para obtener el color y la talla de un producto
	fun obtenerColorDeProducto(productoId: Int): ColorAPI? {
    	return colores.value.firstOrNull { colorApi ->
    	    colorApi.zapatos.any { it.id == productoId }
    	}
	}

	fun obtenerTallaDeProducto(productoId: Int): TallaAPI? {
	    return tallas.value.firstOrNull { tallaApi ->
	        tallaApi.zapatos.any { it.id == productoId }
	    }
	}
}