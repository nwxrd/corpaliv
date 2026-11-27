package cl.duoc.corpaliv_grupo9.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingBag
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import cl.duoc.corpaliv_grupo9.data.remote.RetrofitInstance
import cl.duoc.corpaliv_grupo9.model.Producto
import cl.duoc.corpaliv_grupo9.model.productosAlbaLab

class CatalogoRepository(private val api: ApiService = RetrofitInstance.api) {
    suspend fun listar(): List<Producto> {
        val plantillas = productosAlbaLab.associateBy { it.id }
        return api.listarProductos().map { remoto ->
            val plantilla = plantillas[remoto.id]
            Producto(
                id = remoto.id,
                nombre = remoto.nombre,
                descripcion = remoto.descripcion,
                valor = remoto.valor,
                icono = plantilla?.icono ?: Icons.Filled.ShoppingBag,
                imagen = plantilla?.imagen
            )
        }
    }
}