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
import cl.duoc.corpaliv_grupo9.model.formatearPesos

// ViewModel COMPARTIDO: lo usan Formulario, Confirmación y Panel (Guía 11)
class RegistroViewModel : ViewModel() {

    // ---------- Formulario ----------
    private val _formulario = MutableStateFlow(FormularioUiState())
    val formulario: StateFlow<FormularioUiState> = _formulario.asStateFlow()

    // ---------- Registros guardados (para el Panel) ----------
    private val _registros = MutableStateFlow<List<Registro>>(emptyList())
    val registros: StateFlow<List<Registro>> = _registros.asStateFlow()

    // ---------- Último registro enviado (para la Confirmación) ----------
    private val _ultimoRegistro = MutableStateFlow<Registro?>(null)
    val ultimoRegistro: StateFlow<Registro?> = _ultimoRegistro.asStateFlow()

    // ---------- Cambios de campos (cada uno limpia su error) ----------
    fun onNombreChange(valor: String) {
        _formulario.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidosChange(valor: String) {
        _formulario.update { it.copy(apellidos = valor, errores = it.errores.copy(apellidos = null)) }
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
        _formulario.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null)) }
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

    // ---------- Validación global (Guía 11) ----------
    fun validarFormulario(totalCarrito: Int): Boolean {
        val actual = _formulario.value

        val errores = FormularioErrores(
            nombre = if (actual.nombre.isBlank()) "Campo obligatorio" else null,
            apellidos = if (actual.apellidos.isBlank()) "Campo obligatorio" else null,
            correo = when {
                actual.correo.isBlank() -> "Campo obligatorio"
                !actual.correo.contains("@") -> "Correo inválido"
                else -> null
            },
            confirmarCorreo = when {
                actual.confirmarCorreo.isBlank() -> "Campo obligatorio"
                actual.confirmarCorreo != actual.correo -> "Los correos no coinciden"
                else -> null
            },
            telefono = if (actual.telefono.length < 8) "Ingresa un teléfono válido" else null,
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

    // ---------- Envío con carga simulada (Guía 12) ----------
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
                telefono = datos.telefono,
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