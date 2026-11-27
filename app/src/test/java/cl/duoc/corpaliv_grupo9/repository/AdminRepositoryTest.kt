package cl.duoc.corpaliv_grupo9.repository

import android.util.Base64
import cl.duoc.corpaliv_grupo9.data.remote.AdminSession
import cl.duoc.corpaliv_grupo9.data.remote.ApiService
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkStatic
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class AdminRepositoryTest {
    private val api = mockk<ApiService>()
    private val repository = AdminRepository(api)

    @Before
    fun configurarBase64() {
        mockkStatic(Base64::class)
        every { Base64.encodeToString(any<ByteArray>(), Base64.NO_WRAP) } answers {
            java.util.Base64.getEncoder().encodeToString(firstArg())
        }
    }

    @After
    fun limpiarSesion() {
        AdminSession.cerrar()
        unmockkStatic(Base64::class)
    }

    @Test
    fun iniciarSesionValidaCredencialesYGuardaSesion(): Unit = runBlocking {
        coEvery { api.validarSesion(any()) } returns Response.success(Unit)

        assertTrue(repository.iniciarSesion("admin", "secreto"))

        coVerify { api.validarSesion("Basic YWRtaW46c2VjcmV0bw==") }
        assertTrue(AdminSession.obtenerAuthorization().startsWith("Basic "))
    }

    @Test
    fun iniciarSesionRechazadaNoGuardaSesion(): Unit = runBlocking {
        coEvery { api.validarSesion(any()) } returns
            Response.error(401, "unauthorized".toResponseBody())

        assertFalse(repository.iniciarSesion("admin", "incorrecta"))

        coVerify { api.validarSesion(any()) }
        org.junit.Assert.assertThrows(IllegalStateException::class.java) {
            AdminSession.obtenerAuthorization()
        }
    }

    @Test
    fun errorDeRedSePropaga(): Unit = runBlocking {
        coEvery { api.validarSesion(any()) } throws java.io.IOException("offline")

        org.junit.Assert.assertThrows(java.io.IOException::class.java) {
            runBlocking { repository.iniciarSesion("admin", "secreto") }
        }
    }
}
