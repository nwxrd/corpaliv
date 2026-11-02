package cl.duoc.corpaliv_grupo9.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.corpaliv_grupo9.data.CorpalivDatabase
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.repository.RegistroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// ViewModel del Panel de Vinculación (pantalla del administrador)
class PanelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RegistroRepository(CorpalivDatabase.getInstance(application))

    private val _registros = MutableStateFlow<List<Registro>>(emptyList())
    val registros: StateFlow<List<Registro>> = _registros.asStateFlow()


    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()


    fun cargarRegistros() {
        viewModelScope.launch {
            _cargando.value = true
            _registros.value = repository.obtenerTodos()
            _cargando.value = false
        }
    }


    fun cambiarEstado(id: Long, estado: String) {
        viewModelScope.launch {
            repository.cambiarEstado(id, estado)
            _registros.value = repository.obtenerTodos()
        }
    }
}

