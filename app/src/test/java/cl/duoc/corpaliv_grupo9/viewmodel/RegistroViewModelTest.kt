package cl.duoc.corpaliv_grupo9.viewmodel

import cl.duoc.corpaliv_grupo9.MainDispatcherRule
import cl.duoc.corpaliv_grupo9.data.model.SolicitudRequest
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import cl.duoc.corpaliv_grupo9.repository.RegistroRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RegistroViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun formularioValido(viewModel: RegistroViewModel) {
        viewModel.onNombreChange("Ana")
        viewModel.onApellidosChange("Pérez")
        viewModel.onCorreoChange("ana@example.com")
        viewModel.onConfirmarCorreoChange("ana@example.com")
        viewModel.onTelefonoChange("91234567")
        viewModel.onTipoChange(TipoColaboracion.Voluntario)
        viewModel.onMensajeChange("Quiero ayudar.")
    }

    private fun viewModel(repository: RegistroRepository = mockk()) =
        RegistroViewModel(repository) { it.contains("@") && it.substringAfter("@").contains(".") }

    @Test
    fun formularioVacioFallaValidacion(): Unit {
        val viewModel = viewModel()

        assertFalse(viewModel.validarFormulario(totalCarrito = 0))
        assertEquals("Campo obligatorio", viewModel.formulario.value.errores.nombre)
        assertEquals("Campo obligatorio", viewModel.formulario.value.errores.correo)
        assertEquals("Selecciona una opción", viewModel.formulario.value.errores.tipo)
    }

    @Test
    fun validacionRechazaCorreoInvalidoYCorreosDistintos(): Unit {
        val viewModel = viewModel()
        formularioValido(viewModel)
        viewModel.onCorreoChange("correo-invalido")
        viewModel.onConfirmarCorreoChange("otro@example.com")

        assertFalse(viewModel.validarFormulario(totalCarrito = 0))
        assertEquals("Formato de correo inválido", viewModel.formulario.value.errores.correo)
        assertEquals("Los correos no coinciden", viewModel.formulario.value.errores.confirmarCorreo)
    }

    @Test
    fun validacionRequiereMontoMinimoParaDonacion(): Unit {
        val viewModel = viewModel()
        formularioValido(viewModel)
        viewModel.onTipoChange(TipoColaboracion.Donante)
        viewModel.onMontoChange("499")

        assertFalse(viewModel.validarFormulario(totalCarrito = 0))
        assertEquals("El mínimo es 500 CLP", viewModel.formulario.value.errores.monto)
    }

    @Test
    fun camposFiltranCaracteresYTipoNoDonanteLimpiaMonto(): Unit {
        val viewModel = viewModel()

        viewModel.onNombreChange("Ana123 Pérez!")
        viewModel.onApellidosChange("Gómez-2")
        viewModel.onTelefonoChange("12a34567890")
        viewModel.onMontoChange("abc499")
        viewModel.onTipoChange(TipoColaboracion.Voluntario)

        assertEquals("Ana Pérez", viewModel.formulario.value.nombre)
        assertEquals("Gómez", viewModel.formulario.value.apellidos)
        assertEquals("12345678", viewModel.formulario.value.telefono)
        assertEquals("", viewModel.formulario.value.monto)
    }

    @Test
    fun validacionRechazaNombreCortoTelefonoIncorrectoYCompraSinProductos(): Unit {
        val viewModel = viewModel()
        viewModel.onNombreChange("A")
        viewModel.onApellidosChange("B")
        viewModel.onCorreoChange("ana@example.com")
        viewModel.onConfirmarCorreoChange("ana@example.com")
        viewModel.onTelefonoChange("123")
        viewModel.onTipoChange(TipoColaboracion.Comprador)

        assertFalse(viewModel.validarFormulario(totalCarrito = 0))
        assertEquals("Ingresa un nombre válido", viewModel.formulario.value.errores.nombre)
        assertEquals("Ingresa un apellido válido", viewModel.formulario.value.errores.apellidos)
        assertEquals("El número debe tener 8 dígitos", viewModel.formulario.value.errores.telefono)
        assertEquals(
            "Agrega productos al carrito desde el Catálogo",
            viewModel.formulario.value.errores.tipo
        )

        viewModel.onNombreChange("Ana")
        viewModel.onApellidosChange("Pérez")
        viewModel.onTelefonoChange("91234567")
        assertTrue(viewModel.validarFormulario(totalCarrito = 1000))
        assertNull(viewModel.formulario.value.errores.tipo)
    }

    @Test
    fun donacionDeQuinientosPesosEsValida(): Unit {
        val viewModel = viewModel()
        formularioValido(viewModel)
        viewModel.onTipoChange(TipoColaboracion.Donante)
        viewModel.onMontoChange("500")

        assertTrue(viewModel.validarFormulario(totalCarrito = 0))
        assertNull(viewModel.formulario.value.errores.monto)
    }

    @Test
    fun enviarRegistroGuardaDatosYNotificaExito(): Unit = runTest {
        val repository = mockk<RegistroRepository>()
        val guardado = Registro(
            id = 1,
            nombre = "Ana Pérez",
            correo = "ana@example.com",
            telefono = "+569 91234567",
            tipoColaborador = "Voluntariado",
            fecha = "2026-01-01",
            estado = "Nuevo",
            mensaje = "Quiero ayudar."
        )
        coEvery { repository.guardar(any()) } returns guardado
        val viewModel = viewModel(repository)
        formularioValido(viewModel)
        var exitos = 0

        viewModel.enviarRegistro(totalCarrito = 0) { exitos++ }
        advanceUntilIdle()

        coVerify {
            repository.guardar(
                SolicitudRequest(
                    nombre = "Ana Pérez",
                    correo = "ana@example.com",
                    telefono = "+569 91234567",
                    tipoColaborador = "Voluntariado",
                    mensaje = "Quiero ayudar."
                )
            )
        }
        assertEquals(guardado, viewModel.ultimoRegistro.value)
        assertEquals(1, exitos)
        assertFalse(viewModel.formulario.value.enviando)
        assertNull(viewModel.formulario.value.errorEnvio)
    }

    @Test
    fun envioFallidoExponeMensajeYNoNotificaExito(): Unit = runTest {
        val repository = mockk<RegistroRepository>()
        coEvery { repository.guardar(any()) } throws java.io.IOException("offline")
        val viewModel = viewModel(repository)
        formularioValido(viewModel)
        var exitos = 0

        viewModel.enviarRegistro(totalCarrito = 0) { exitos++ }
        advanceUntilIdle()

        assertEquals(
            "No se pudo conectar con el servidor. Inténtalo nuevamente.",
            viewModel.formulario.value.errorEnvio
        )
        assertFalse(viewModel.formulario.value.enviando)
        assertEquals(0, exitos)
    }

    @Test
    fun noEnviaSiFormularioEsInvalido(): Unit = runTest {
        val repository = mockk<RegistroRepository>()
        val viewModel = viewModel(repository)

        viewModel.enviarRegistro(totalCarrito = 0) {}
        advanceUntilIdle()

        coVerify(exactly = 0) { repository.guardar(any()) }
        assertTrue(viewModel.formulario.value.errorEnvio == null)
    }

    @Test
    fun segundoEnvioMientrasElPrimeroEstaEnCursoNoDuplicaSolicitud(): Unit = runTest {
        val repository = mockk<RegistroRepository>()
        val respuesta = CompletableDeferred<Registro>()
        coEvery { repository.guardar(any()) } coAnswers { respuesta.await() }
        val viewModel = viewModel(repository)
        formularioValido(viewModel)

        viewModel.enviarRegistro(totalCarrito = 0) {}
        runCurrent()
        assertTrue(viewModel.formulario.value.enviando)

        viewModel.enviarRegistro(totalCarrito = 0) {}
        runCurrent()
        coVerify(exactly = 1) { repository.guardar(any()) }

        respuesta.complete(
            Registro(
                id = 1,
                nombre = "Ana Pérez",
                correo = "ana@example.com",
                telefono = "+569 91234567",
                tipoColaborador = "Voluntariado",
                fecha = "2026-01-01",
                estado = "Nuevo"
            )
        )
        advanceUntilIdle()
        assertFalse(viewModel.formulario.value.enviando)
    }
}
