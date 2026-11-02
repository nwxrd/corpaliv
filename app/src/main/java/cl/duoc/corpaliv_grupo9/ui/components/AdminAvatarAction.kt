package cl.duoc.corpaliv_grupo9.ui.components

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import cl.duoc.corpaliv_grupo9.viewmodel.PerfilViewModel

@Composable
fun AdminAvatarAction(
    perfilViewModel: PerfilViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val imagenPerfil by perfilViewModel.imagenUri.collectAsState()
    var mostrarOpcionesFoto by rememberSaveable { mutableStateOf(false) }
    var permisoCamaraDenegado by rememberSaveable { mutableStateOf(false) }

    val selectorImagen = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri -> perfilViewModel.seleccionarImagen(uri) }
    val capturaCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { exito -> perfilViewModel.confirmarFotoCamara(exito) }
    val solicitudPermisoCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            capturaCamara.launch(perfilViewModel.crearTempPictureUri(context))
        } else {
            permisoCamaraDenegado = true
        }
    }

    fun abrirCamara() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            capturaCamara.launch(perfilViewModel.crearTempPictureUri(context))
        } else {
            solicitudPermisoCamara.launch(Manifest.permission.CAMERA)
        }
    }

    IconButton(
        onClick = { mostrarOpcionesFoto = true },
        modifier = modifier.size(48.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(colors.surfaceVariant)
                    .border(1.dp, colors.primary.copy(alpha = 0.45f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (imagenPerfil != null) {
                    AsyncImage(
                        model = imagenPerfil,
                        contentDescription = "Cambiar foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Cambiar foto de perfil",
                        tint = colors.onSurfaceVariant
                    )
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(colors.primary)
                    .border(1.dp, colors.surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    tint = colors.onPrimary,
                    modifier = Modifier.size(11.dp)
                )
            }
        }
    }

    if (mostrarOpcionesFoto) {
        AlertDialog(
            onDismissRequest = { mostrarOpcionesFoto = false },
            title = { Text("Foto de perfil") },
            text = { Text("Selecciona de dónde quieres obtener la foto.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarOpcionesFoto = false
                        selectorImagen.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    }
                ) { Text("Almacenamiento") }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarOpcionesFoto = false
                        abrirCamara()
                    }
                ) { Text("Usar cámara") }
            }
        )
    }

    if (permisoCamaraDenegado) {
        AlertDialog(
            onDismissRequest = { permisoCamaraDenegado = false },
            title = { Text("Permiso de cámara") },
            text = { Text("Para tomar una foto, permite el acceso a la cámara en los ajustes de la aplicación.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        permisoCamaraDenegado = false
                        context.startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.packageName, null)
                            )
                        )
                    }
                ) { Text("Abrir ajustes") }
            },
            dismissButton = {
                TextButton(onClick = { permisoCamaraDenegado = false }) { Text("Cancelar") }
            }
        )
    }
}