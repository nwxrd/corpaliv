package cl.duoc.corpaliv_grupo9.repository

import cl.duoc.corpaliv_grupo9.data.model.EstadoRequest
import cl.duoc.corpaliv_grupo9.data.model.RegistroDto
import cl.duoc.corpaliv_grupo9.data.model.SolicitudRequest
import cl.duoc.corpaliv_grupo9.data.remote.AdminSession
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class RegistroRepositoryTest {
    private val api = mockk<ApiService>()
    private val repository = RegistroRepository(api)
    private val solicitud = SolicitudRequest(
        nombre = "Ana Pérez",
        correo = "ana@example.com",
        telefono = "+569 12345678",
        tipoColaborador = "Voluntariado",
        mensaje = "Quiero ayudar."
    )
    private val dto = RegistroDto(
        id = 7,
        nombre = solicitud.nombre,
        correo = solicitud.correo,
        telefono = solicitud.telefono,
        tipoColaborador = solicitud.tipoColaborador,
        fecha = "2026-01-01",
        estado = "Nuevo",
        mensaje = solicitud.mensaje
    )

    @After
    fun limpiarSesion() {
        AdminSession.cerrar()
    }

    @Test
    fun guardarEnviaSolicitudYConvierteRespuesta(): Unit = runBlocking {
        coEvery { api.crearSolicitud(solicitud) } returns dto

        val registro = repository.guardar(solicitud)

        assertEquals(dto.toDomain(), registro)
        assertEquals(dto.id, registro.id)
        assertEquals(dto.nombre, registro.nombre)
        assertEquals(dto.correo, registro.correo)
        assertEquals(dto.telefono, registro.telefono)
        assertEquals(dto.tipoColaborador, registro.tipoColaborador)
        assertEquals(dto.fecha, registro.fecha)
        assertEquals(dto.estado, registro.estado)
        assertEquals(dto.mensaje, registro.mensaje)
        coVerify { api.crearSolicitud(solicitud) }
    }

    @Test
    fun obtenerTodosUsaSesionAdministrativaYConvierteRespuestas(): Unit = runBlocking {
        AdminSession.iniciar("Basic test-token")
        coEvery { api.listarSolicitudes("Basic test-token") } returns listOf(dto)

        assertEquals(listOf(dto.toDomain()), repository.obtenerTodos())

        coVerify { api.listarSolicitudes("Basic test-token") }
    }

    @Test
    fun cambiarEstadoEnviaEstadoYConvierteRespuesta(): Unit = runBlocking {
        AdminSession.iniciar("Basic test-token")
        val actualizado = dto.copy(estado = "Contactado")
        val estado = EstadoRequest("Contactado")
        coEvery {
            api.cambiarEstado("Basic test-token", dto.id, estado)
        } returns actualizado

        assertEquals("Contactado", estado.estado)
        assertEquals(actualizado.toDomain(), repository.cambiarEstado(dto.id, estado.estado))
    }

    @Test
    fun eliminarExitosamenteNoLanzaError(): Unit = runBlocking {
        AdminSession.iniciar("Basic test-token")
        coEvery {
            api.eliminarSolicitud("Basic test-token", dto.id)
        } returns Response.success(Unit)

        repository.eliminar(dto.id)

        coVerify { api.eliminarSolicitud("Basic test-token", dto.id) }
    }

    @Test
    fun eliminarConRespuestaHttpFallidaLanzaHttpException(): Unit = runBlocking {
        AdminSession.iniciar("Basic test-token")
        coEvery {
            api.eliminarSolicitud("Basic test-token", dto.id)
        } returns Response.error(500, "error".toResponseBody())

        assertThrows(HttpException::class.java) {
            runBlocking { repository.eliminar(dto.id) }
        }
    }
}
