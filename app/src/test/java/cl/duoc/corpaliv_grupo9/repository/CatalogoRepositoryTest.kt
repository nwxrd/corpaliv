package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.model.ProductoDto
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class CatalogoRepositoryTest {
    private val api = mockk<ApiService>()
    private val repository = CatalogoRepository(api)

    @Test
    fun listarConvierteProductosRemotosAlModeloDeDominio(): Unit = runBlocking {
        coEvery { api.listarProductos() } returns listOf(
            ProductoDto(1, "Producto remoto", "Descripción", 1200),
            ProductoDto(99, "Producto nuevo", "Sin plantilla local", 500)
        )

        val productos = repository.listar()

        assertEquals(2, productos.size)
        assertEquals(1, productos[0].id)
        assertEquals("Producto remoto", productos[0].nombre)
        assertEquals("Descripción", productos[0].descripcion)
        assertEquals(1200, productos[0].valor)
        assertEquals(null, productos[1].imagen)
        assertEquals("Producto nuevo", productos[1].nombre)
        assertEquals(99, productos[1].id)
        assertEquals("Sin plantilla local", productos[1].descripcion)
        assertEquals(500, productos[1].valor)
    }

    @Test
    fun errorDeApiSePropaga(): Unit = runBlocking {
        coEvery { api.listarProductos() } throws IllegalStateException("fallo")

        assertThrows(IllegalStateException::class.java) {
            runBlocking { repository.listar() }
        }
    }
}
