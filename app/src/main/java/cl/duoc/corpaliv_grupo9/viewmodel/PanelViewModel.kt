package cl.duoc.corpaliv_grupo9.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.repository.RegistroRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel del Panel de Vinculación (pantalla del administrador)
class PanelViewModel(
    private val repository: RegistroRepository = RegistroRepository()
) : ViewModel() {

    private val _registros = MutableStateFlow<List<Registro>>(emptyList())
    val registros: StateFlow<List<Registro>> = _registros.asStateFlow()


    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarRegistros() {
        viewModelScope.launch {
            _cargando.value = true
            try {
                _registros.value = repository.obtenerTodos()
                _error.value = null
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _error.value = "No se pudo cargar la información. Revisa la conexión con el servidor."
            } finally {
                _cargando.value = false
            }
        }
    }


    fun cambiarEstado(id: Long, estado: String) {
        viewModelScope.launch {
            try {
                repository.cambiarEstado(id, estado)
                _registros.value = repository.obtenerTodos()
                _error.value = null
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _error.value = "No se pudo actualizar el estado de la solicitud."
            }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            try {
                repository.eliminar(id)
                _registros.value = repository.obtenerTodos()
                _error.value = null
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _error.value = "No se pudo eliminar la solicitud."
            }
        }
    }
}
