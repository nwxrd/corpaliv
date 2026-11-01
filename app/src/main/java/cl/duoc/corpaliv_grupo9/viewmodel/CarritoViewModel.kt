package cl.duoc.corpaliv_grupo9.viewmodel

import androidx.lifecycle.ViewModel
import cl.duoc.corpaliv_grupo9.model.ItemCarrito
import cl.duoc.corpaliv_grupo9.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel(){

    private val _items = MutableStateFlow<List<ItemCarrito>>(emptyList())
    private val _mostrarPedido = MutableStateFlow(false)
    val mostrarPedido: StateFlow<Boolean> = _mostrarPedido.asStateFlow()
    val items: StateFlow<List<ItemCarrito>> = _items.asStateFlow()

    fun abrirPedido(){
        _mostrarPedido.value = true
    }

    fun cerrarPedido(){
        _mostrarPedido.value = false
    }
    fun agregar(producto: Producto, cantidad: Int){
        _items.update { lista ->
            val yaEsta = lista.any{ it.producto.id == producto.id }
            if(yaEsta){
                lista.map {
                    if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
                }
            }else{
                lista+ ItemCarrito(producto,cantidad)
            }
        }
    }

    fun quitar(producto: Producto) {
        _items.update { lista -> lista.filter { it.producto.id != producto.id } }
    }
    fun sumarUno(producto: Producto){_items.update{lista->
        lista.map{if(it.producto.id == producto.id)
            it.copy(cantidad = it.cantidad +1)else it}
        }
    }
    fun restarUno(producto: Producto){
        _items.update{lista->
            lista.map{
                if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad-1) else it
            }.filter {it.cantidad >0}
        }
    }

    fun vaciar() {
        _items.value = emptyList()
    }
}