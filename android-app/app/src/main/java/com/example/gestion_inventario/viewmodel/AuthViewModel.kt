package com.example.gestion_inventario.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.model.AgregarProductoUiState
import com.example.gestion_inventario.model.LoginUiState
import com.example.gestion_inventario.model.ReportarProblemaUiState
import com.example.gestion_inventario.model.RegistroUsuarioUiState


// Gestion de estado e implementacion de logica de negocio de formularios
// Se agrega inyeccion de dependencia al establecer parametro 'repository'
class AuthViewModel(
	private val usuarioRepository: UsuarioRepository,
	private val productoRepository: ProductoRepository
): ViewModel() {
	// Esta variable manejara el estado y podra ser observado por los componentes UI
	// del login (gracias a StateFlow) que permitira que reaccionen ante sus cambios.
	// Es privado y mutable, por lo que solo sera modificado solo desde esta clase
	private val _login = MutableStateFlow(value = LoginUiState())

	// Manejo del estado para el usuario que ha iniciado correctamente de sesion
	// Se inicializa de esta forma porque se usara solo para saber si fue autenticado
	// o no el usuario. Si no lo esta, entonces '_usuarioLogueado == null'
	private val _usuarioLogueado = MutableStateFlow<UsuarioEntity?>(null)

	// Manejo de estado de formulario de reporte de problemas
	private val _reportarProblema = MutableStateFlow(value = ReportarProblemaUiState())

	// Manejo de estado de formulario de Agregar Producto
	private val _registrarProducto = MutableStateFlow(value = AgregarProductoUiState())

	private val _registrarUsuario = MutableStateFlow(value = RegistroUsuarioUiState())

	// Esta variable publica es la que se usara para acceder al estado del login desde fuera
	// de la clase AuthViewModel. Tambien es un StateFlow de tipo LoginUiState, pero no es mutable.
	val login: StateFlow<LoginUiState> = _login

	// Variable publica para acceder a estado de usuario logueado desde afuera de esta clase
	val usuarioLogueado: StateFlow<UsuarioEntity?> get() = _usuarioLogueado

	// Variable publica para acceder desde afuera al estado de reporte de probelmas
	val reportarProblema: StateFlow<ReportarProblemaUiState> = _reportarProblema

	// Variable publica para acceder a estado de atributos de agregar producto desde otra clase
	val registrarProducto: StateFlow<AgregarProductoUiState> = _registrarProducto

	val registrarUsuario: StateFlow<RegistroUsuarioUiState> = _registrarUsuario


	/*==== Funciones para validar login. ====*/
	// Validar si campo esta vacio
	fun estaVacio(campo: String?): Boolean {
		if(campo.isNullOrBlank()) return true else return false
	}

	// Validacion de campo 'email'
	fun validarEmail(email: String?): String? {
		if(estaVacio(email)) return "Debe ingresar un email."

		val patronCorrecto = Patterns.EMAIL_ADDRESS.matcher(email!!).matches()

		if(!patronCorrecto) return "Formato de email invalido." else return null
	}

	// Validacion de campo 'password'
	fun validarPasswordLogin(password: String?): String? {
		if(estaVacio(password)) return "Debe ingresar su password asociado a su email." else return null
	}


	/*==== Funciones para la gestion de campos de login. ====*/
	// Gestion de cambio de estado de 'email'
	fun onEmailChange(valorEmail: String) {
		_login.update { it.copy(email = valorEmail, emailError = validarEmail(valorEmail)) }
	}

	// Gestion de cambio de estado de 'password'
	fun onPasswordLoginChange(valorPassword: String) {
		_login.update { it.copy(password = valorPassword, passwordError = validarPasswordLogin(valorPassword)) }
	}

	/*==== Funcion para permitir envio de formulario login. ====*/
	suspend fun canSubmitLogin(): Boolean {
		val estadoLogin = _login.value // Se obtiene el estado actual del objeto 'LoginUiState' con sus atributos

		// Actualizacion del estado: Usado para mostrar mensaje de error si se intenta
		// iniciar sesion sin haber llenado los campos de texto
		val msgErrorUpdatedEmail = validarEmail(estadoLogin.email)
		val msgErrorUpdatedPass = validarPasswordLogin(estadoLogin.password)

		_login.update{it.copy(emailError = msgErrorUpdatedEmail, passwordError = msgErrorUpdatedPass)}

		// 1era validacion: Que no hayan errores tales como campos vacios (email y password)
		if((msgErrorUpdatedEmail != null) || (msgErrorUpdatedPass != null)) {
			return false
		}

		// 2da validacion: Ver si credenciales son las correctas ('.isSuccess == true' si lo son, sino devolvera 'false')
		val resultBusquedaUsuario = usuarioRepository.validarLogin(estadoLogin.email.trim(), estadoLogin.password)

		// Nuevo: Se actualiza el estado del usuario logueado
		// Uso de metodo 'getOrNull()': Si resultado es exitoso, entonces se obtiene al `UsuarioEntity`
		// que inicio sesion. Si el resultado no es exitoso, entonces 'getOrNull()' retorna 'null'
		_usuarioLogueado.value = resultBusquedaUsuario.getOrNull()

		return resultBusquedaUsuario.isSuccess
	}

	/*==== Funcion para limpiar campos de texto del login. ====*/
	fun limpiarCamposLogin() {
		_login.value = LoginUiState() // Se establece el estado inicial del formulario
	}

	/*==== Funciones para validar campos de reporte de problema. ====*/
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

	fun onDetalleProblemaChange(valorDetalleProblema: String) {
		_reportarProblema.update{ it.copy(detalleProblema = valorDetalleProblema) }
	}

	// Funcion para actualizar estado del tipo de problema
	fun actualizarTipoProblema(valorTipoProblema: String) {
		//_reportarProblema.value = _reportarProblema.value.copy(tipoProblema = valorTipoProblema)
		_reportarProblema.update{ it.copy(tipoProblema = valorTipoProblema, tipoProblemaError = null) }
	}

	// Funcion para actualizar estado de nivel de prioridad del problema
	// Es como el para el 'onValueChange', pero para los radio buttons de esta parte de la vista
	fun actualizarNivelProblema(valorNivelProblema: String) {
		_reportarProblema.update{ it.copy(nivelProblema = valorNivelProblema, nivelProblemaError = null) }
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


	/*== Funciones para validar campos de formulario Agregar Producto == */
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
		if(estaVacio(descripcion)) return "La descripción es obligatoria"
		val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		return if(!regex.matches(descripcion)) "Solo se aceptan letras y espacios" else null
	}

	fun validarTalla(talla: String): String? {
		if (estaVacio(talla)) return "La talla es obligatoria"

		val regex = Regex("^[0-9]+(\\.[0-9]+)?$") // Acepta enteros o decimales

		return if (!regex.matches(talla)) {
			"La talla solo puede contener números (ej: 38 o 38.5)"
		} else null
	}

	fun validarColor(color: String): String?{
		if(estaVacio(color)) return "El nombre es obligatorio"
		val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		return if(!regex.matches(color)) "Solo se aceptan letras y espacios" else null
	}

	//Validaciones numericas
	fun validarPrecio(precio: String): String? {
		if (estaVacio(precio)) return "El precio es obligatorio"

		// Solo números con puntos cada tres dígitos, ej: 10.000 o 1.000.000
		val regex = Regex("^[0-9]{1,3}(\\.[0-9]{3})*$")

		return if (!regex.matches(precio)) {
			"El precio debe tener el formato 10.000 (solo puntos como separadores de miles)"
		} else null
	}

	fun validarCantidad(cantidad: String): String? {
		if (estaVacio(cantidad)) return "La cantidad es obligatoria"

		val regex = Regex("^[0-9]+$") // Solo números enteros positivos

		return if (!regex.matches(cantidad)) {
			"La cantidad solo puede contener números enteros"
		} else null
	}



	fun onCodigoChange(valorCodigo: String) {
		//val filtered = value.filter { it.isLetter() || it.isWhitespace() }
		_registrarProducto.update { it.copy(codigo = valorCodigo, errorCodigo = validarCodigo(valorCodigo)) }
	}

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

	// Funcion para limpiar campos de registro de productos
	fun limpiarCamposRegistroProd() {
		_registrarProducto.value = AgregarProductoUiState()
	}



	// Funcion para validar si se puede registrar el producto
	fun canSubmitRegistrarProd():Boolean {
		val estadoRegistrarProd = _registrarProducto.value

		val errorCodigoUpdated = validarCodigo(estadoRegistrarProd.codigo)
		val errorNombreProductoUpdated = validarNombreProducto(estadoRegistrarProd.nombreProducto)
		val errorDescripcionUpdated = validarDescripcion(estadoRegistrarProd.descripcion)
		val errorTallaUpdated = validarTalla(estadoRegistrarProd.talla)
		val errorColorUpdated = validarColor(estadoRegistrarProd.color)
		val errorPrecioUpdated = validarPrecio(estadoRegistrarProd.precio)
		val errorCantidadUpdated = validarCantidad(estadoRegistrarProd.cantidad)

		_registrarProducto.update{
			it.copy(
				errorCodigo = errorCodigoUpdated,
				errorNombreProducto = errorNombreProductoUpdated,
				errorDescripcion = errorDescripcionUpdated,
				errorTalla = errorTallaUpdated,
				errorColor = errorColorUpdated,
				errorPrecio = errorPrecioUpdated,
				errorCantidad = errorCantidadUpdated
			)
		}

		if(
			errorCodigoUpdated != null ||
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
	fun submitRegistroProducto() {
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
	}

	//Funciones para validar los campos de productos que no deben estar vacios

	fun validarNombre(nombre: String): String?{
		if(nombre.isBlank()) return "El nombre es obligatorio"
		val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		return if(!regex.matches(nombre)) "Solo se aceptan letras y espacios" else null
	}

	fun validarApellido(apellido: String): String?{
		if(apellido.isBlank()) return "El Apellido es obligatorio"
		val regex = Regex("^[A-Za-zÁÉÍÓÚÑáéíóúñ ]+$")
		return if(!regex.matches(apellido)) "Solo se aceptan letras y espacios" else null
	}

	fun validarEmail1(email: String): String?{
		if(email.isBlank()) return "El correo es obligatorio"
		val ok = Patterns.EMAIL_ADDRESS.matcher(email).matches()
		return if(!ok) "Formato de correo Inválido" else null }

	fun validarContrasena(contrasena: String): String? {
		if (contrasena.isBlank()) return "La contraseña es obligatoria"

		// Mínimo 12 caracteres, una mayúscula, una minúscula y un número
		val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{12,}$")

		return if (!regex.matches(contrasena)) {
			"Debe tener al menos 12 caracteres, una mayúscula, una minúscula y un número"
		} else null
	}
	fun validarConfirmar(contrasena: String, confirmar: String): String? {
		if(confirmar.isBlank()) return "Confirmar es obligatorio"
		return if (contrasena != confirmar) "Las contraseñas no coinciden" else null
	}
	fun estaVacia(campo: String?): Boolean {
		return campo.isNullOrBlank()
	}

	fun validarTipoUsuario(tipoUsuario: String?): String? {
		return if (estaVacia(tipoUsuario))
			"Debe elegir un tipo de usuario"
		else
			null
	}

	fun actualizarTipoUsuario(valorTipoUsuario: String) {
		//_reportarProblema.value = _reportarProblema.value.copy(tipoProblema = valorTipoProblema)
		_registrarUsuario.update{ it.copy(tipoUsuario = valorTipoUsuario, errorTipoUsuario = null) }
	}

	/*== Funciones para gestionar estados de campos de formulario Registrar Usuarios == */


	fun onNombreChange(valorNombre: String) {
		_registrarUsuario.update { it.copy(nombre = valorNombre , errorNombre = validarNombre(valorNombre)) }

	}

	fun onApellidoChange(valorApellido: String) {
		_registrarUsuario.update { it.copy(apellido = valorApellido, errorApellido = validarApellido(valorApellido)) }

	}

	fun onEmail1Change(valorEmail1: String) {
		_registrarUsuario.update { it.copy(email = valorEmail1, errorEmail = validarEmail1(valorEmail1)) }

	}

	fun onContrasenaChange(valorContrasena: String) {
		_registrarUsuario.update { it.copy(contrasena = valorContrasena, errorContrasena = validarContrasena(valorContrasena)) }
		_registrarUsuario.update { it.copy(errorConfirmar = validarConfirmar(it.contrasena, it.confirmar)) }

	}

	fun onConfirmarChange(valorConfirmar: String) {
		_registrarUsuario.update { it.copy(confirmar = valorConfirmar, errorConfirmar = validarConfirmar(it.contrasena, valorConfirmar)) }

	}

	fun onTipoUsuarioChange(valorTipoUsuario: String) {
		_registrarUsuario.update { it.copy(tipoUsuario = valorTipoUsuario, errorTipoUsuario = valorTipoUsuario) }

	}

	fun limpiarCamposRegistroUsua() {
		_registrarUsuario.value = RegistroUsuarioUiState()
	}


	// Lista de usuarios registrados

	private val _usuarios = MutableStateFlow<List<UsuarioEntity>>(emptyList())
	val usuarios: StateFlow<List<UsuarioEntity>> = _usuarios


	// Cargar los usuarios desde el repositorio
	fun cargarUsuarios() {
		viewModelScope.launch {
			usuarioRepository.obtenerUsuarios().collect { lista ->
				_usuarios.value = lista
			}
		}
	}

	private val _usuarioSeleccionado = MutableStateFlow<UsuarioEntity?>(null)
	val usuarioSeleccionado: StateFlow<UsuarioEntity?> = _usuarioSeleccionado

	fun cargarUsuarioPorId(id: Long) {
		viewModelScope.launch {
			_usuarioSeleccionado.value = usuarioRepository.obtenerUsuarioPorId(id)
		}
	}

	fun eliminarUsuario(id: Long) {
		viewModelScope.launch {
			usuarioRepository.eliminarUsuario(id)
		}
	}


	fun actualizarUsuario(usuario: UsuarioEntity, onSuccess: () -> Unit) {
		viewModelScope.launch {
			usuarioRepository.actualizarUsuario(usuario)
			onSuccess()
		}
	}






	fun canSubmitRegistrarUsua():Boolean {
		val estadoRegistrarUsua = _registrarUsuario.value

		val errorNombreUpdated = validarNombre(estadoRegistrarUsua.nombre)
		val errorApellidoUpdated = validarApellido(estadoRegistrarUsua.apellido)
		val errorEmailUpdated = validarEmail1(estadoRegistrarUsua.email)
		val errorContrasenaUpdated = validarContrasena(estadoRegistrarUsua.contrasena)
		val errorConfirmarUpdated = validarConfirmar(estadoRegistrarUsua.contrasena, confirmar = estadoRegistrarUsua.confirmar)
		val errorTipoUsuarioUpdate = validarTipoUsuario(estadoRegistrarUsua.tipoUsuario)


		_registrarUsuario.update{
			it.copy(
				errorNombre = errorNombreUpdated,
				errorApellido = errorApellidoUpdated,
				errorEmail = errorEmailUpdated,
				errorContrasena = errorContrasenaUpdated,
				errorConfirmar = errorConfirmarUpdated,
				errorTipoUsuario = errorTipoUsuarioUpdate
			)
		}

		if(
			errorNombreUpdated != null ||
			errorApellidoUpdated != null ||
			errorEmailUpdated != null ||
			errorContrasenaUpdated != null ||
			errorConfirmarUpdated != null  ||
			errorTipoUsuarioUpdate != null
		) {
			return false
		}

		return true
	}

	// Funcion para registrar producto
	fun submitRegistroUsuario() {
		val estadoRegistrarUsua = _registrarUsuario.value

		// Se lanza corrutina por funcion de registro de Repository (es asincrona)
		viewModelScope.launch {
			usuarioRepository.registrarUsuario(
				nombre = estadoRegistrarUsua.nombre,
				apellido = estadoRegistrarUsua.apellido,
				email = estadoRegistrarUsua.email,
				password = estadoRegistrarUsua.contrasena,
				tipoUsuario = estadoRegistrarUsua.tipoUsuario
			)
		}
	}
}