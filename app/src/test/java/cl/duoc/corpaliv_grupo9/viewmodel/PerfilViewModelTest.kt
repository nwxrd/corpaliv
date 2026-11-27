package cl.duoc.corpaliv_grupo9.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.mockk
import io.mockk.unmockkStatic
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.io.File

class PerfilViewModelTest {
    @After
    fun limpiarFileProvider() {
        unmockkStatic(FileProvider::class)
    }

    @Test
    fun seleccionarImagenActualizaPerfilYNullNoLoBorra() {
        val viewModel = PerfilViewModel()
        val uri = mockk<Uri>()

        viewModel.seleccionarImagen(uri)
        viewModel.seleccionarImagen(null)

        assertEquals(uri, viewModel.imagenUri.value)
    }

    @Test
    fun confirmarFotoSinUriTemporalNoCambiaLaImagen() {
        val viewModel = PerfilViewModel()
        val uri = mockk<Uri>()
        viewModel.seleccionarImagen(uri)

        viewModel.confirmarFotoCamara(exito = true)

        assertEquals(uri, viewModel.imagenUri.value)
    }

    @Test
    fun confirmarFotoFallidaDejaImagenSinCambios() {
        val viewModel = PerfilViewModel()

        viewModel.confirmarFotoCamara(exito = false)

        assertNull(viewModel.imagenUri.value)
    }

    @Test
    fun crearUriTemporalLaGuardaYPermiteConfirmarCaptura() {
        val directory = File(
            System.getProperty("java.io.tmpdir"),
            "corpaliv-profile-test-${System.nanoTime()}"
        ).apply { check(mkdirs()) }
        val context = mockk<Context>()
        val uri = mockk<Uri>()
        every { context.getExternalFilesDir("Pictures") } returns directory
        every { context.packageName } returns "cl.duoc.corpaliv_grupo9"
        mockkStatic(FileProvider::class)
        every {
            FileProvider.getUriForFile(context, "cl.duoc.corpaliv_grupo9.provider", any())
        } returns uri
        val viewModel = PerfilViewModel()

        try {
            assertEquals(uri, viewModel.crearTempPictureUri(context))
            assertEquals(uri, viewModel.tempCameraUri)

            viewModel.confirmarFotoCamara(exito = true)

            assertEquals(uri, viewModel.imagenUri.value)
        } finally {
            directory.listFiles()?.forEach { it.delete() }
            directory.delete()
        }
    }

    @Test
    fun fotoFallidaConUriTemporalNoActualizaPerfil() {
        val directory = File(
            System.getProperty("java.io.tmpdir"),
            "corpaliv-profile-test-${System.nanoTime()}"
        ).apply { check(mkdirs()) }
        val context = mockk<Context>()
        val uri = mockk<Uri>()
        every { context.getExternalFilesDir("Pictures") } returns directory
        every { context.packageName } returns "cl.duoc.corpaliv_grupo9"
        mockkStatic(FileProvider::class)
        every {
            FileProvider.getUriForFile(context, "cl.duoc.corpaliv_grupo9.provider", any())
        } returns uri
        val viewModel = PerfilViewModel()

        try {
            viewModel.crearTempPictureUri(context)
            viewModel.confirmarFotoCamara(exito = false)

            assertNull(viewModel.imagenUri.value)
        } finally {
            directory.listFiles()?.forEach { it.delete() }
            directory.delete()
        }
    }
}
