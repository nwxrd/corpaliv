package cl.duoc.corpaliv_grupo9.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cl.duoc.corpaliv_grupo9.model.Producto
import cl.duoc.corpaliv_grupo9.model.productosAlbaLab
import cl.duoc.corpaliv_grupo9.repository.CatalogoRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel : ViewModel() {
    private val repository = CatalogoRepository()
    private val _productos = MutableStateFlow(productosAlbaLab)
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarProductos() {
        viewModelScope.launch {
            try {
                _productos.value = repository.listar()
                _error.value = null
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                _error.value = "Sin conexión con el catálogo remoto; se muestran los productos disponibles."
            }
        }
    }
}