package cl.duoc.corpaliv_grupo9.viewmodel

import cl.duoc.corpaliv_grupo9.MainDispatcherRule
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.repository.RegistroRepository
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PanelViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val registro = Registro(
        id = 1,
        nombre = "Ana Pérez",
        correo = "ana@example.com",
        telefono = "+569 12345678",
        tipoColaborador = "Voluntariado",
        fecha = "2026-01-01",
        estado = "Nuevo"
    )

    @Test
    fun cargarRegistrosCargaDatosYFinalizaIndicador() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.obtenerTodos() } returns listOf(registro)
        val viewModel = PanelViewModel(repository)

        viewModel.cargarRegistros()
        advanceUntilIdle()

        assertEquals(listOf(registro), viewModel.registros.value)
        assertFalse(viewModel.cargando.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun cargarRegistrosAnteFallaExponeErrorYFinalizaIndicador() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.obtenerTodos() } throws IllegalStateException("offline")
        val viewModel = PanelViewModel(repository)

        viewModel.cargarRegistros()
        advanceUntilIdle()

        assertFalse(viewModel.cargando.value)
        assertEquals(
            "No se pudo cargar la información. Revisa la conexión con el servidor.",
            viewModel.error.value
        )
    }

    @Test
    fun cambiarEstadoActualizaYRecargaSolicitudes() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.cambiarEstado(registro.id, "Contactado") } returns
            registro.copy(estado = "Contactado")
        coEvery { repository.obtenerTodos() } returns listOf(registro.copy(estado = "Contactado"))
        val viewModel = PanelViewModel(repository)

        viewModel.cambiarEstado(registro.id, "Contactado")
        advanceUntilIdle()

        coVerifyOrder {
            repository.cambiarEstado(registro.id, "Contactado")
            repository.obtenerTodos()
        }
        assertEquals("Contactado", viewModel.registros.value.single().estado)
        assertNull(viewModel.error.value)
    }

    @Test
    fun cambiarEstadoAnteFallaExponeError() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.cambiarEstado(registro.id, "Contactado") } throws
            IllegalStateException("offline")
        val viewModel = PanelViewModel(repository)

        viewModel.cambiarEstado(registro.id, "Contactado")
        advanceUntilIdle()

        assertEquals(
            "No se pudo actualizar el estado de la solicitud.",
            viewModel.error.value
        )
    }

    @Test
    fun eliminarRegistroEliminaYRecargaSolicitudes() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.eliminar(registro.id) } returns Unit
        coEvery { repository.obtenerTodos() } returns emptyList()
        val viewModel = PanelViewModel(repository)

        viewModel.eliminar(registro.id)
        advanceUntilIdle()

        assertEquals(emptyList<Registro>(), viewModel.registros.value)
        assertNull(viewModel.error.value)
    }

    @Test
    fun eliminarAnteFallaExponeError() = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.eliminar(registro.id) } throws IllegalStateException("offline")
        val viewModel = PanelViewModel(repository)

        viewModel.eliminar(registro.id)
        advanceUntilIdle()

        assertEquals("No se pudo eliminar la solicitud.", viewModel.error.value)
    }
}
