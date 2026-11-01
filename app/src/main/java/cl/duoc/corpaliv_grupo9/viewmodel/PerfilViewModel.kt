package cl.duoc.corpaliv_grupo9.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class PerfilViewModel : ViewModel() {

    // StateFlow para exponer de forma reactiva la imagen actual a la interfaz
    private val _imagenUri = MutableStateFlow<Uri?>(null)
    val imagenUri: StateFlow<Uri?> = _imagenUri.asStateFlow()

    // Variable temporal para guardar la URI que generamos para la cámara
    var tempCameraUri: Uri? = null
        private set

    // Función para actualizar la imagen seleccionada desde la galería
    fun seleccionarImagen(uri: Uri?) {
        if (uri != null) {
            _imagenUri.value = uri
        }
    }

    // Función para generar un archivo temporal y obtener su URI usando el FileProvider
    fun crearTempPictureUri(context: Context): Uri {
        val tempFile = File.createTempFile(
            "picture_${System.currentTimeMillis()}",
            ".jpg",
            context.getExternalFilesDir("Pictures")
        )
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            tempFile
        )
        tempCameraUri = uri
        return uri
    }

    // Función para confirmar y actualizar el estado si la cámara tomó la foto correctamente
    fun confirmarFotoCamara(exito: Boolean) {
        if (exito && tempCameraUri != null) {
            _imagenUri.value = tempCameraUri
        }
    }
}