package cl.duoc.corpaliv_grupo9.viewmodel

import cl.duoc.corpaliv_grupo9.model.productosAlbaLab
import cl.duoc.corpaliv_grupo9.model.formatearPesos
import cl.duoc.corpaliv_grupo9.model.totalCarrito
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CarritoViewModelTest {
    private val producto = productosAlbaLab.first()

    @Test
    fun agregarProductoYRepetirloAcumulaCantidad() {
        val viewModel = CarritoViewModel()

        viewModel.agregar(producto, 2)
        viewModel.agregar(producto, 3)

        assertEquals(5, viewModel.items.value.single().cantidad)
    }

    @Test
    fun sumarYRestarActualizaCantidadYTotal() {
        val viewModel = CarritoViewModel()
        viewModel.agregar(producto, 1)

        viewModel.sumarUno(producto)
        assertEquals(2, viewModel.items.value.single().cantidad)
        assertEquals(producto.valor * 2, totalCarrito(viewModel.items.value))

        viewModel.restarUno(producto)
        assertEquals(1, viewModel.items.value.single().cantidad)
    }

    @Test
    fun restarUltimaUnidadQuitaProducto() {
        val viewModel = CarritoViewModel()
        viewModel.agregar(producto, 1)

        viewModel.restarUno(producto)

        assertTrue(viewModel.items.value.isEmpty())
    }

    @Test
    fun quitarYVaciarEliminanProductos() {
        val viewModel = CarritoViewModel()
        val segundoProducto = productosAlbaLab[1]
        viewModel.agregar(producto, 1)
        viewModel.agregar(segundoProducto, 1)

        viewModel.quitar(producto)
        assertEquals(listOf(segundoProducto.id), viewModel.items.value.map { it.producto.id })

        viewModel.vaciar()
        assertTrue(viewModel.items.value.isEmpty())
        assertEquals(0, totalCarrito(viewModel.items.value))
    }

    @Test
    fun abrirYCerrarPedidoActualizaSuEstado() {
        val viewModel = CarritoViewModel()

        viewModel.abrirPedido()
        assertTrue(viewModel.mostrarPedido.value)

        viewModel.cerrarPedido()
        assertEquals(false, viewModel.mostrarPedido.value)
    }

    @Test
    fun operacionesConProductoAusenteNoAgreganNiModificanElementos() {
        val viewModel = CarritoViewModel()
        val productoAusente = productosAlbaLab[1]

        viewModel.sumarUno(productoAusente)
        viewModel.restarUno(productoAusente)
        viewModel.quitar(productoAusente)

        assertTrue(viewModel.items.value.isEmpty())
    }

    @Test
    fun formateaMontosEnPesosChilenos() {
        assertEquals("$10.990", formatearPesos(10990))
        assertEquals("$0", formatearPesos(0))
    }
}
