package com.example.gestion_inventario.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

import com.example.gestion_inventario.model.LoginUiState

// Gestion de estado e implementacion de logica de negocio de formularios
class AuthViewModel: ViewModel() {
	// Esta variable manejara el estado y podra ser observado por los componentes UI
	// del login (gracias a StateFlow) que permitira que reaccionen ante sus cambios.
	// Es privado y mutable, por lo que solo sera modificado solo desde esta clase
	private val _login = MutableStateFlow(value = LoginUiState())
	
	// Esta variable publica es la que se usara para acceder al estado del login desde fuera
	// de la clase AuthViewModel. Tambien es un StateFlow de tipo LoginUiState, pero no es mutable.
	val login: StateFlow<LoginUiState> = _login

	
	/*==== Funciones para validar login. ====*/
	// Validar si campo esta vacio
	fun estaVacio(campo: String): Boolean {
		if(campo.isBlank()) return true else return false
	}

	// Validacion de campo 'email'
	fun validarEmail(email: String): String? {
		if(estaVacio(email)) return "Debe ingresar un email."

		val patronCorrecto = Patterns.EMAIL_ADDRESS.matcher(email).matches()

		if(!patronCorrecto) return "Formato de email invalido." else return null
	}

	// Validacion de campo 'password'
	fun validarPasswordLogin(password: String): String? {
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
	fun canSubmitLogin(valorEmail: String, valorPassword: String): Boolean {
		val validacionEmail = validarEmail(valorEmail)
		val validacionPassword = validarPasswordLogin(valorPassword)

		// Se actualiza el estado de los errores (util cuando se presiona boton 'iniciar sesion' y no hay datos ingresados aun)
		_login.update{it.copy(emailError = validacionEmail, passwordError = validacionPassword)}

		if((validacionEmail == null) && (validacionPassword == null)) {
			return true
		} else {
			return false
		}
	}

	/*==== Funcion para limpiar campos de texto del login. ====*/
	fun limpiarCamposLogin() {
		_login.value = LoginUiState() // Se establece el estado inicial del formulario
	}

}