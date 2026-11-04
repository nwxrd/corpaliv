package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.corpaliv_grupo9.model.Registro
import cl.duoc.corpaliv_grupo9.ui.theme.BlueContainer
import cl.duoc.corpaliv_grupo9.ui.theme.BlueOnContainer
import cl.duoc.corpaliv_grupo9.ui.theme.Green100
import cl.duoc.corpaliv_grupo9.ui.theme.Green600
import cl.duoc.corpaliv_grupo9.ui.theme.Slate200
import cl.duoc.corpaliv_grupo9.ui.theme.Slate700
import cl.duoc.corpaliv_grupo9.viewmodel.PanelViewModel
import cl.duoc.corpaliv_grupo9.viewmodel.PerfilViewModel

// Filtros del panel: texto del botón → estado que filtra (null = todos)
private val filtros = listOf(
    "Todos" to null,
    "Nuevos" to "Nuevo",
    "Contactados" to "Contactado",
    "Concretados" to "Concretado"
)

@Composable
fun AdminPanelScreen(
    onCerrarSesion: () -> Unit,
    panelViewModel: PanelViewModel = viewModel(),
    perfilViewModel: PerfilViewModel
) {
    val colors = MaterialTheme.colorScheme
    val registros by panelViewModel.registros.collectAsState()
    val cargando by panelViewModel.cargando.collectAsState()

    // Filtro elegido (Guía 12: rememberSaveable)
    var filtroActivo by rememberSaveable { mutableStateOf("Todos") }

    // Cada vez que se abre el panel, lee los registros de SQLite
    LaunchedEffect(Unit) { panelViewModel.cargarRegistros() }

    // Lista filtrada (Guía 12: derivedStateOf)
    val visibles by remember(registros, filtroActivo) {
        derivedStateOf {
            val estado = filtros.first { it.first == filtroActivo }.second
            if (estado == null) registros else registros.filter { it.estado == estado }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 16.dp)
    ) {
        // ---------- Encabezado ----------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Panel Vinculación",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onBackground
                )
                Text(
                    "${registros.size} registros",
                    fontSize = 12.sp,
                    color = colors.onSurfaceVariant
                )
            }
            TextButton(onClick = onCerrarSesion) {
                Text("Cerrar sesión", fontSize = 12.sp)
            }
        }

        // ---------- Filtros ----------
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filtros.forEach { (texto, _) ->
                BotonFiltro(
                    texto = texto,
                    activo = filtroActivo == texto,
                    onClick = { filtroActivo = texto }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------- Lista ----------
        when {
            cargando -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = colors.primary)
                }
            }
            visibles.isEmpty() -> {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text("No hay registros en este filtro", color = colors.onSurfaceVariant)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(visibles, key = { it.id }) { registro ->
                        TarjetaRegistro(
                            registro = registro,
                            onContactar = { panelViewModel.cambiarEstado(registro.id, "Contactado") },
                            onConcretar = { panelViewModel.cambiarEstado(registro.id, "Concretado") }
                        )
                    }
                }
            }
        }
    }

}

// Botón de filtro con color animado (Guía 12)
@Composable
private fun BotonFiltro(texto: String, activo: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val fondo by animateColorAsState(
        targetValue = if (activo) colors.onBackground else colors.surface,
        label = "fondoFiltro"
    )
    val colorTexto by animateColorAsState(
        targetValue = if (activo) colors.background else colors.onSurface,
        label = "textoFiltro"
    )

    Text(
        texto,
        fontSize = 13.sp,
        fontWeight = if (activo) FontWeight.Bold else FontWeight.Medium,
        color = colorTexto,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(fondo)
            .border(1.dp, colors.outline, RoundedCornerShape(50))
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 8.dp)
    )
}

// Tarjeta de cada registro
@Composable
private fun TarjetaRegistro(
    registro: Registro,
    onContactar: () -> Unit,
    onConcretar: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Card(
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, colors.outline),
        colors = CardDefaults.cardColors(containerColor = colors.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Nombre + etiqueta de estado
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    registro.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface,
                    modifier = Modifier.weight(1f)
                )
                EtiquetaEstado(registro.estado)
            }
            Text(
                "${registro.correo} • ${registro.telefono}",
                fontSize = 12.sp,
                color = colors.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Recuadro con el interés y el mensaje
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surfaceVariant, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            ) {
                Text(
                    "Interés: ${registro.tipoColaborador}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.onSurface
                )
                if (registro.mensaje.isNotBlank()) {
                    Text(
                        registro.mensaje,
                        fontSize = 12.sp,
                        color = colors.onSurfaceVariant
                    )
                }
            }

            // Fecha + acciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    registro.fecha,
                    fontSize = 11.sp,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                if (registro.estado == "Nuevo") {
                    TextButton(onClick = onContactar) { Text("Contactar", fontSize = 12.sp) }
                }
                if (registro.estado != "Concretado") {
                    TextButton(onClick = onConcretar) { Text("Concretar", fontSize = 12.sp) }
                }
            }
        }
    }
}

// Etiqueta de color según el estado, con colores de Color.kt (animación: Guía 12)
@Composable
private fun EtiquetaEstado(estado: String) {
    val (fondoObjetivo, textoObjetivo) = when (estado) {
        "Contactado" -> Slate200 to Slate700
        "Concretado" -> Green100 to Green600
        else -> BlueContainer to BlueOnContainer     // "Nuevo"
    }
    val fondo by animateColorAsState(fondoObjetivo, label = "fondoEstado")
    val texto by animateColorAsState(textoObjetivo, label = "textoEstado")

    Text(
        estado,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = texto,
        modifier = Modifier
            .background(fondo, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    )
}