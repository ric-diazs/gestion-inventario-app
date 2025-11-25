package com.example.gestion_inventario.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_inventario.data.local.entity.ProductoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.gestion_inventario.data.local.entity.UsuarioEntity
import com.example.gestion_inventario.data.remote.model.IdObject
import com.example.gestion_inventario.data.remote.model.TipoUsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioAPI
import com.example.gestion_inventario.data.remote.model.UsuarioSolicitud
import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.model.AgregarProductoUiState
import com.example.gestion_inventario.model.LoginUiState
import com.example.gestion_inventario.model.ReportarProblemaUiState
import com.example.gestion_inventario.model.RegistroUsuarioUiState


// Gestion de estado e implementacion de logica de negocio de formularios
// Se agrega inyeccion de dependencia al establecer parametro 'repository'
class AuthViewModel(private val usuarioRepository: UsuarioRepository): ViewModel() {
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
	private val _registrarUsuario = MutableStateFlow(value = RegistroUsuarioUiState())

	private val _usuarios = MutableStateFlow<List<UsuarioEntity>>(emptyList())

	private val _usuarioSeleccionado = MutableStateFlow<UsuarioEntity?>(null)

	private val _usuariosApi = MutableStateFlow<List<UsuarioAPI>>(emptyList())

	private val _usuarioApi = MutableStateFlow<UsuarioAPI?>(null)

	private val _idObject = MutableStateFlow<IdObject?>(null)

	private val _tiposUsuario = MutableStateFlow<List<TipoUsuarioAPI>>(emptyList())

    // Esta variable publica es la que se usara para acceder al estado del login desde fuera
	// de la clase AuthViewModel. Tambien es un StateFlow de tipo LoginUiState, pero no es mutable.
	val login: StateFlow<LoginUiState> = _login

	// Variable publica para acceder a estado de usuario logueado desde afuera de esta clase
	val usuarioLogueado: StateFlow<UsuarioEntity?> get() = _usuarioLogueado

	// Variable publica para acceder desde afuera al estado de reporte de probelmas
	val reportarProblema: StateFlow<ReportarProblemaUiState> = _reportarProblema

	// Variable publica para acceder a estado de atributos de agregar producto desde otra clase
	val registrarUsuario: StateFlow<RegistroUsuarioUiState> = _registrarUsuario

	val usuarios: StateFlow<List<UsuarioEntity>> = _usuarios

	val usuarioSeleccionado: StateFlow<UsuarioEntity?> = _usuarioSeleccionado

	val usuariosApi : StateFlow<List<UsuarioAPI>> = _usuariosApi

	val usuarioApi : StateFlow<UsuarioAPI?> = _usuarioApi

	val idObject : StateFlow<IdObject?> = _idObject

	val tiposUsuario : StateFlow<List<TipoUsuarioAPI>> = _tiposUsuario

	/*
	=====================================
	Llamado automatico de funciones a API
	=====================================
	*/
	init {
		cargarTiposUsuarioApi()
		cargarUsuariosApi()
	}


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

	//Funciones para validar que los campos de registro de usuarios no esten vacios
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

	fun actualizarTipoUsuario(valorTipoUsuario: String, valorIdTipoUsuario: Int) {
		//_reportarProblema.value = _reportarProblema.value.copy(tipoProblema = valorTipoProblema)
		_registrarUsuario.update{ it.copy(tipoUsuario = valorTipoUsuario, idTipoUsuario = valorIdTipoUsuario, errorTipoUsuario = null) }
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
		//_registrarUsuario.update { it.copy(errorConfirmar = validarConfirmar(it.contrasena, it.confirmar)) }

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


	// Cargar los usuarios desde el repositorio
	fun cargarUsuarios() {
		viewModelScope.launch {
			usuarioRepository.obtenerUsuarios().collect { lista ->
				_usuarios.value = lista
			}
		}
	}

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

	/*
	=======================================================
	        Funciones que interactuan con API (Retrofit)
	=======================================================
	*/
	fun cargarTiposUsuarioApi() {
		viewModelScope.launch {
			try{
				_tiposUsuario.value = usuarioRepository.obtenerTiposUsuarioAPI()
			} catch(e: Exception) {
				println("Error al obtener los tipos de usuario: ${e.localizedMessage}")
			}
		}
	}

	fun cargarUsuariosApi() {
		viewModelScope.launch {
			try{
				_usuariosApi.value = usuarioRepository.obtenerUsuariosAPI()
			} catch(e: Exception) {
				println("Error al obtener los usuarios: ${e.localizedMessage}")
			}
		}
	}

	fun cargarUsuarioApiPorId(id: Int){
		viewModelScope.launch{
			try{
				_usuarioApi.value = usuarioRepository.obtenerUsuarioAPIPorId(id)
			} catch(e: Exception) {
				println("Error al obtener a usuario de id ${id}: ${e.localizedMessage}")
			}
		}
	}

	fun submitRegistroUsuarioApi() {
		val estadoRegistrarUsuaApi = _registrarUsuario.value

		viewModelScope.launch{
			usuarioRepository.registrarUsuarioAPI(
				nombre = estadoRegistrarUsuaApi.nombre,
				apellidos = estadoRegistrarUsuaApi.apellido,
				correo = estadoRegistrarUsuaApi.email,
				password = estadoRegistrarUsuaApi.contrasena,
				idTipoUsuario = estadoRegistrarUsuaApi.idTipoUsuario!!
			)
		}
	}

	fun submitActualizarUsuarioAPI(id: Int, usuarioActualizar: UsuarioSolicitud){
		viewModelScope.launch{
			usuarioRepository.actualizarUsuarioAPI(id, usuarioActualizar)
		}
	}

	fun submitEliminarUsuarioAPI(id: Int) {
		viewModelScope.launch{
			usuarioRepository.eliminarUsuarioAPI(id)
		}
	}

	// Funciones para obtener el tipo de usuario de un usuario
	fun obtenerTipoUsuarioDeUsuario(usuarioId: Int) : TipoUsuarioAPI? {
		return tiposUsuario.value.firstOrNull { tUsuarioApi ->
			tUsuarioApi.usuarios.any {it.id == usuarioId}
		}
	}
}