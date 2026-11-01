package cl.duoc.corpaliv_grupo9.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.corpaliv_grupo9.model.FormularioErrores
import cl.duoc.corpaliv_grupo9.model.FormularioUiState
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import android.util.Patterns
import cl.duoc.corpaliv_grupo9.model.formatearPesos

// ViewModel lo usan Formulario y Confirmación
class RegistroViewModel : ViewModel() {

    // ---------- Formulario ----------
    private val _formulario = MutableStateFlow(FormularioUiState())
    val formulario: StateFlow<FormularioUiState> = _formulario.asStateFlow()


    private val _registros = MutableStateFlow<List<Registro>>(emptyList())
    val registros: StateFlow<List<Registro>> = _registros.asStateFlow()


    private val _ultimoRegistro = MutableStateFlow<Registro?>(null)
    val ultimoRegistro: StateFlow<Registro?> = _ultimoRegistro.asStateFlow()

    // ---------- Cambios de campos (cada uno limpia su error) ----------
    fun onNombreChange(valor: String) {
        val soloLetras = valor.filter {it.isLetter() || it == ' '}
        _formulario.update { it.copy(nombre = soloLetras, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidosChange(valor: String) {
        val soloLetras = valor.filter {it.isLetter() || it == ' '}
        _formulario.update { it.copy(apellidos = soloLetras, errores = it.errores.copy(apellidos = null)) }
    }

    fun onCorreoChange(valor: String) {
        _formulario.update { it.copy(correo = valor.trim(), errores = it.errores.copy(correo = null)) }
    }

    fun onConfirmarCorreoChange(valor: String) {
        _formulario.update {
            it.copy(confirmarCorreo = valor.trim(), errores = it.errores.copy(confirmarCorreo = null))
        }
    }

    fun onTelefonoChange(valor: String) {
        val soloNumeros = valor.filter { it.isDigit() }.take(8)
        _formulario.update { it.copy(telefono = soloNumeros, errores = it.errores.copy(telefono = null)) }
    }

    fun onTipoChange(valor: TipoColaboracion) {
        _formulario.update {
            it.copy(
                tipo = valor,
                // Si deja de ser Donante, se borra el monto
                monto = if (valor == TipoColaboracion.Donante) it.monto else "",
                errores = it.errores.copy(tipo = null, monto = null)
            )
        }
    }

    fun onMontoChange(valor: String) {
        // Solo números
        val soloNumeros = valor.filter { it.isDigit() }
        _formulario.update { it.copy(monto = soloNumeros, errores = it.errores.copy(monto = null)) }
    }

    fun onMensajeChange(valor: String) {
        _formulario.update { it.copy(mensaje = valor) }
    }

    // ---------- Validación global
    fun validarFormulario(totalCarrito: Int): Boolean {
        val actual = _formulario.value

        val errores = FormularioErrores(
            nombre = when {
                actual.nombre.isBlank() -> "Campo obligatorio"
                actual.nombre.trim().length < 2 -> "Ingresa un nombre válido"
                else -> null
            },
            apellidos = when {
                actual.apellidos.isBlank() -> "Campo obligatorio"
                actual.apellidos.trim().length < 2 -> "Ingresa un apellido válido"
                else -> null
            },
            correo = when {
                actual.correo.isBlank() -> "Campo obligatorio"
                !Patterns.EMAIL_ADDRESS.matcher(actual.correo).matches() -> "Formato de correo inválido"
                else -> null
            },
            confirmarCorreo = when {
                actual.confirmarCorreo.isBlank() -> "Campo obligatorio"
                actual.confirmarCorreo != actual.correo -> "Los correos no coinciden"
                else -> null
            },
            telefono = when {
                actual.telefono.isBlank() -> "Campo obligatorio"
                actual.telefono.length != 8 -> "El número debe tener 8 dígitos"
                else -> null
            },
            tipo = when {
                actual.tipo == null -> "Selecciona una opción"
                actual.tipo == TipoColaboracion.Comprador && totalCarrito == 0 ->
                    "Agrega productos al carrito desde el Catálogo"
                else -> null
            },
            monto = validarMonto(actual)
        )

        val hayErrores = listOfNotNull(
            errores.nombre, errores.apellidos, errores.correo, errores.confirmarCorreo,
            errores.telefono, errores.tipo, errores.monto
        ).isNotEmpty()

        _formulario.update { it.copy(errores = errores) }
        return !hayErrores
    }
    // El monto solo se valida si eligió "Donación"
    private fun validarMonto(actual: FormularioUiState): String? {
        if (actual.tipo != TipoColaboracion.Donante) return null
        if (actual.monto.isBlank()) return "Ingresa el monto a donar"

        val valor = actual.monto.toIntOrNull() ?: 0
        return if (valor < 500) "El mínimo es 500 CLP" else null
    }

    // ---------- Envío con carga simulada
    fun enviarRegistro(totalCarrito: Int, onExito: () -> Unit) {
        if (_formulario.value.enviando || !validarFormulario(totalCarrito)) return

        viewModelScope.launch {
            _formulario.update { it.copy(enviando = true) }
            delay(1000) // simula el envío

            val datos = _formulario.value
            val tipo = datos.tipo ?: TipoColaboracion.Voluntario

            // Se agrega al mensaje el monto donado o el total de la compra
            val mensajeFinal = when (tipo) {
                TipoColaboracion.Donante -> "Monto: ${datos.monto} CLP. ${datos.mensaje}"
                TipoColaboracion.Comprador -> "Compra: ${formatearPesos(totalCarrito)}. ${datos.mensaje}"
                else -> datos.mensaje
            }

            val nuevo = Registro(
                id = System.currentTimeMillis(),
                nombre = "${datos.nombre} ${datos.apellidos}",
                correo = datos.correo,
                telefono = "+569 ${datos.telefono}",
                tipoColaborador = tipo.tipoRegistro,
                fecha = LocalDate.now().toString(),
                estado = "Nuevo",
                mensaje = mensajeFinal
            )

            _registros.update { listOf(nuevo) + it }
            _ultimoRegistro.value = nuevo
            _formulario.value = FormularioUiState() // limpia el formulario
            onExito()
        }
    }
}