package com.example.gestion_inventario.ui.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.delay
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gestion_inventario.data.repository.ProductoRepository
import com.example.gestion_inventario.data.repository.RegistroRepository
import com.example.gestion_inventario.data.repository.ReporteRepository
import com.example.gestion_inventario.domain.validation.validateConfirm
import com.example.gestion_inventario.domain.validation.validateContra
import com.example.gestion_inventario.domain.validation.validateDescripcion
import com.example.gestion_inventario.domain.validation.validateEmail
import com.example.gestion_inventario.domain.validation.validateLastName
import com.example.gestion_inventario.domain.validation.validateName2
import com.example.gestion_inventario.domain.validation.validateNombrepro
import kotlinx.coroutines.launch


data class RegistroState(                                 // Estado de la pantalla Registro Usuario Admin

    val nombre: String = "",                              // 2) Nombre
    val apellido: String = "",                            // 3) Apellido
    val email: String = "",                               // 4) Email
    val contrasena: String = "",                          // 5) Contraseña
    val confirmar: String = "",                           // 6) Confirmar contraseña
    val tipoUsuario: String = "",                         // 7) Tipo de usuario

    // Errores por campo
    val errorNombre: String? = null,
    val errorApellido: String? = null,
    val errorEmail: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmar: String? = null,

    val isSubmitting: Boolean = false,                    // Flag de carga
    val canSubmit: Boolean = false,                       // Habilitar botón
    val success: Boolean = false,                         // Resultado OK
    val errorMsg: String? = null                          // Error global (ej: duplicado)
)

// ----------------- VIEWMODEL REGISTRO USUARIO ADMIN -----------------

class RegistroAdminViewModel(
    private val repository: RegistroRepository                // ✅ Repositorio real (Room/SQLite)
) : ViewModel() {

    private val _registro = MutableStateFlow(RegistroState())  // Estado interno
    val registro: StateFlow<RegistroState> = _registro          // Exposición inmutable

    // ----------------- HANDLERS -----------------

    fun onNombreChange(value: String) {                   // Handler nombre
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registro.update { it.copy(nombre = filtered, errorNombre = validateName2(filtered)) }
        recomputeCanSubmit()
    }

    fun onApellidoChange(value: String) {                 // Handler apellido
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registro.update { it.copy(apellido = filtered, errorApellido = validateLastName(filtered)) }
        recomputeCanSubmit()
    }

    fun onEmailChange(value: String) {                    // Handler email
        _registro.update { it.copy(email = value, errorEmail = validateEmail(value)) }
        recomputeCanSubmit()
    }

    fun onContrasenaChange(value: String) {               // Handler contraseña
        _registro.update { it.copy(contrasena = value, errorContrasena = validateContra(value)) }
        _registro.update { it.copy(errorConfirmar = validateConfirm(it.contrasena, it.confirmar)) }
        recomputeCanSubmit()
    }

    fun onConfirmarChange(value: String) {                // Handler confirmación
        _registro.update { it.copy(confirmar = value, errorConfirmar = validateConfirm(it.contrasena, value)) }
        recomputeCanSubmit()
    }

    fun onTipoUsuarioChange(value: String) {              // Handler tipo de usuario
        _registro.update { it.copy(tipoUsuario = value) }
        recomputeCanSubmit()
    }

    // ----------------- LÓGICA -----------------

    private fun recomputeCanSubmit() {                    // Habilitar botón "Registrar" si todo OK
        val s = _registro.value
        val noErrors = listOf(
            s.errorNombre, s.errorApellido,
            s.errorEmail, s.errorContrasena, s.errorConfirmar
        ).all { it == null }
        val filled = s.nombre.isNotBlank() && s.apellido.isNotBlank() &&
                s.email.isNotBlank() && s.contrasena.isNotBlank() && s.confirmar.isNotBlank() &&
                s.tipoUsuario.isNotBlank()
        _registro.update { it.copy(canSubmit = noErrors && filled) }
    }

    fun submitRegister() {                                // Acción de registro (con repositorio real)
        val s = _registro.value
        if (!s.canSubmit || s.isSubmitting) return
        viewModelScope.launch {
            _registro.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            delay(700)                                    // Simulamos IO

            // ✅ Inserción real en base de datos mediante repositorio
            val result = repository.register(
                nombre = s.nombre.trim(),
                apellido = s.apellido.trim(),
                email = s.email.trim(),
                contrasena = s.contrasena,
                tipoUsuario = s.tipoUsuario.trim()
            )

            _registro.update {
                if (result.isSuccess) {
                    it.copy(isSubmitting = false, success = true, errorMsg = null)
                } else {
                    it.copy(
                        isSubmitting = false,
                        success = false,
                        errorMsg = result.exceptionOrNull()?.message ?: "No se pudo registrar"
                    )
                }
            }
        }
    }

    fun clearRegistroResult() {                           // Limpia banderas tras navegar
        _registro.update { it.copy(success = false, errorMsg = null) }
    }
}



//-----------------------------------------------------------------
//-------------------------------AGREGAR PRODUCTO------------------

data class RegistroEstado(                                 // Estado de la pantalla Registro Usuario Admin
    val codigo: String = "",
    val nombrepro: String = "",                              // 2) Nombre
    val descripcion: String = "",                            // 3) Apellido
    val talla: String = "",                               // 4) Email
    val color: String = "",                          // 5) Contraseña
    val precio: String = "",                           // 6) Confirmar contraseña
    val cantidad: String = "",                         // 7) Tipo de usuario

    // Errores por campo
    val errorCodigo: String? = null,
    val errorNombrepro: String? = null,
    val errorDescripcion: String? = null,
    val errorTalla: String? = null,
    val errorColor: String? = null,
    val errorPrecio: String? = null,
    val errorCantidad: String? = null,

    val isSubmitting: Boolean = false,                    // Flag de carga
    val canSubmit: Boolean = false,                       // Habilitar botón
    val success: Boolean = false,                         // Resultado OK
    val errorMsg: String? = null                          // Error global (ej: duplicado)
)

// ----------------- VIEWMODEL AGREGAR PRODUCTO -----------------

class ProductoAdminViewModel(
    private val repository: ProductoRepository                // ✅ Repositorio real (Room/SQLite)
) : ViewModel() {

    private val _registropro = MutableStateFlow(RegistroEstado())  // Estado interno
    val registropro : StateFlow<RegistroEstado> = _registropro          // Exposición inmutable

    // ----------------- HANDLERS -----------------

    fun onCodigoChange(value: String) {                   // Handler Codigo
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registropro.update { it.copy(codigo = filtered) }
        recomputeCanSubmit()
    }

    fun onNombreproChange(value: String) {                 // Handler Nombre Producto
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registropro.update { it.copy(nombrepro = filtered, errorNombrepro = validateNombrepro(filtered)) }
        recomputeCanSubmit()
    }

    fun onDescripcionChange(value: String) {                    // Handler Descripcion
        _registropro.update { it.copy(descripcion = value, errorDescripcion = validateDescripcion(value)) }
        recomputeCanSubmit()
    }

    fun onTallaChange(value: String) {               // Handler Talla
        _registropro.update { it.copy(talla = value) }
        recomputeCanSubmit()
    }

    fun onColorChange(value: String) {                // Handler Color
        _registropro.update { it.copy(color = value) }
        recomputeCanSubmit()
    }

    fun onPrecioChange(value: String) {                // Handler Precio
        _registropro.update { it.copy(precio = value) }
        recomputeCanSubmit()
    }

    fun onCantidadChange(value: String) {                // Handler Cantidad
        _registropro.update { it.copy(cantidad = value) }
        recomputeCanSubmit()
    }


    // ----------------- LÓGICA -----------------

    private fun recomputeCanSubmit() {                    // Habilitar botón "Registrar" si todo OK
        val s = _registropro.value
        val noErrors = listOf(
            s.errorNombrepro,
            s.errorDescripcion, s.errorTalla,
            s.errorPrecio
        ).all { it == null }
        val filled = s.codigo.isNotBlank() && s.nombrepro.isNotBlank() &&
                s.descripcion.isNotBlank() && s.talla.isNotBlank() && s.color.isNotBlank() &&
                s.precio.isNotBlank() &&
                s.cantidad.isNotBlank()
        _registropro.update { it.copy(canSubmit = noErrors && filled) }
    }

    fun submitRegister() {                                // Acción de registro (con repositorio real)
        val s = _registropro.value
        if (!s.canSubmit || s.isSubmitting) return
        viewModelScope.launch {
            _registropro.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            delay(700)                                    // Simulamos IO

            // ✅ Inserción real en base de datos mediante repositorio
            val result = repository.registerpro(
                codigo = s.codigo.trim(),
                nombrepro = s.nombrepro.trim(),
                descripcion = s.descripcion.trim(),
                talla = s.talla.trim(),
                color  = s.color.trim(),
                precio = s.precio.trim(),
                cantidad = s.cantidad.trim()
            )

            _registropro.update {
                if (result.isSuccess) {
                    it.copy(isSubmitting = false, success = true, errorMsg = null)
                } else {
                    it.copy(
                        isSubmitting = false,
                        success = false,
                        errorMsg = result.exceptionOrNull()?.message ?: "No se pudo registrar"
                    )
                }
            }
        }
    }

    fun clearRegistroResult() {                           // Limpia banderas tras navegar
        _registropro.update { it.copy(success = false, errorMsg = null) }
    }
}


//-----------------------------------------------------------------
//-------------------------------REPORTE------------------

data class RegistroEstadoReporte(                                 // Estado de la pantalla Registro Usuario Admin
    val nombrerep: String = "",
    val emailrep: String = "",
    val problema: String = "",
    val descripcionrep: String = "",

    // Errores por campo
    val errorNombrerep: String? = null,
    val errorEmailrep: String? = null,

    val isSubmitting: Boolean = false,                    // Flag de carga
    val canSubmit: Boolean = false,                       // Habilitar botón
    val success: Boolean = false,                         // Resultado OK
    val errorMsg: String? = null                          // Error global (ej: duplicado)
)

// ----------------- VIEWMODEL AGREGAR PRODUCTO -----------------

class ReportarProblemaViewModel(
    private val repository: ReporteRepository                // ✅ Repositorio real (Room/SQLite)
) : ViewModel() {

    private val _registrorepo = MutableStateFlow(RegistroEstadoReporte())  // Estado interno
    val registrorepo : StateFlow<RegistroEstadoReporte> = _registrorepo          // Exposición inmutable

    // ----------------- HANDLERS -----------------

    fun onNombrerepChange(value: String) {                   // Handler Codigo
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registrorepo.update { it.copy(nombrerep = filtered) }
        recomputeCanSubmit()
    }

    fun onEmailrepChange(value: String) {                   // Handler Codigo
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registrorepo.update { it.copy(emailrep = filtered) }
        recomputeCanSubmit()
    }

    fun onProblemaChange(value: String) {                   // Handler Codigo
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registrorepo.update { it.copy(problema = filtered) }
        recomputeCanSubmit()
    }

    fun onDescripcionrepChange(value: String) {                   // Handler Codigo
        val filtered = value.filter { it.isLetter() || it.isWhitespace() }
        _registrorepo.update { it.copy(descripcionrep = filtered) }
        recomputeCanSubmit()
    }



    // ----------------- LÓGICA -----------------

    private fun recomputeCanSubmit() {                    // Habilitar botón "Registrar" si todo OK
        val s = _registrorepo.value
        val noErrors = listOf(
            s.errorNombrerep,
            s.errorEmailrep
        ).all { it == null }
        val filled = s.nombrerep.isNotBlank() && s.emailrep.isNotBlank()
                && s.problema.isNotBlank() && s.descripcionrep.isNotBlank()
        _registrorepo.update { it.copy(canSubmit = noErrors && filled) }
    }

    fun submitRegister() {                                // Acción de registro (con repositorio real)
        val s = _registrorepo.value
        if (!s.canSubmit || s.isSubmitting) return
        viewModelScope.launch {
            _registrorepo.update { it.copy(isSubmitting = true, errorMsg = null, success = false) }
            delay(700)                                    // Simulamos IO

            // ✅ Inserción real en base de datos mediante repositorio
            val result = repository.registerepo(
                nombrerep = s.nombrerep.trim(),
                emailrep = s.emailrep.trim(),
                descripcionrep = s.descripcionrep.trim()
            )
            _registrorepo.update {
                if (result.isSuccess) {
                    it.copy(isSubmitting = false, success = true, errorMsg = null)
                } else {
                    it.copy(
                        isSubmitting = false,
                        success = false,
                        errorMsg = result.exceptionOrNull()?.message ?: "No se pudo registrar"
                    )
                }
            }
        }
    }

    fun clearRegistroResult() {                           // Limpia banderas tras navegar
        _registrorepo.update { it.copy(success = false, errorMsg = null) }
    }
}
