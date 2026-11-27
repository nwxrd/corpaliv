package cl.duoc.corpaliv_grupo9.viewmodel

import cl.duoc.corpaliv_grupo9.MainDispatcherRule
import cl.duoc.corpaliv_grupo9.model.productosAlbaLab
import cl.duoc.corpaliv_grupo9.repository.CatalogoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CatalogoViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun cargarProductosActualizaCatalogoYLimpiaError() = runTest {
        val repository = mockk<CatalogoRepository>()
        val productos = productosAlbaLab.take(2)
        coEvery { repository.listar() } returns productos
        val viewModel = CatalogoViewModel(repository)

        viewModel.cargarProductos()
        advanceUntilIdle()

        assertEquals(productos, viewModel.productos.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun cargarProductosAnteFallaExponeErrorYConservaCatalogoLocal() = runTest {
        val repository = mockk<CatalogoRepository>()
        coEvery { repository.listar() } throws IllegalStateException("offline")
        val viewModel = CatalogoViewModel(repository)

        viewModel.cargarProductos()
        advanceUntilIdle()

        assertEquals(productosAlbaLab, viewModel.productos.value)
        assertEquals(
            "Sin conexión con el catálogo remoto; se muestran los productos disponibles.",
            viewModel.error.value
        )
    }
}
