package com.example.gestion_inventario.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import com.example.gestion_inventario.data.repository.UsuarioRepository
import com.example.gestion_inventario.model.LoginUiState

// Gestion de estado e implementacion de logica de negocio de formularios
// Se agrega inyeccion de dependencia al establecer parametro 'repository'
class AuthViewModel(private val repository: UsuarioRepository): ViewModel() {
	// Esta variable manejara el estado y podra ser observado por los componentes UI
	// del login (gracias a StateFlow) que permitira que reaccionen ante sus cambios.
	// Es privado y mutable, por lo que solo sera modificado solo desde esta clase
	private val _login = MutableStateFlow(value = LoginUiState())
	
	// Esta variable publica es la que se usara para acceder al estado del login desde fuera
	// de la clase AuthViewModel. Tambien es un StateFlow de tipo LoginUiState, pero no es mutable.
	val login: StateFlow<LoginUiState> = _login

	
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
		val resultBusquedaUsuario = repository.validarLogin(estadoLogin.email.trim(), estadoLogin.password)
    	return resultBusquedaUsuario.isSuccess
	}

	/*==== Funcion para limpiar campos de texto del login. ====*/
	fun limpiarCamposLogin() {
		_login.value = LoginUiState() // Se establece el estado inicial del formulario
	}

}