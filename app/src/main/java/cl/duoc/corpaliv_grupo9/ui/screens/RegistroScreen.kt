package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.model.TipoColaboracion
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.viewmodel.RegistroViewModel
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import cl.duoc.corpaliv_grupo9.model.formatearPesos
import cl.duoc.corpaliv_grupo9.model.totalCarrito
import cl.duoc.corpaliv_grupo9.viewmodel.CarritoViewModel
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    carritoViewModel: CarritoViewModel,
    onRegistroExitoso: () -> Unit,
    onVerPedido: ()-> Unit
) {

    val estado by viewModel.formulario.collectAsState()
    val carrito by carritoViewModel.items.collectAsState()
    val total = totalCarrito(carrito)
    val colors = MaterialTheme.colorScheme
    var menuAbierto by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Text(
            text = "¡Eres genial! Gracias por tu compromiso con la Discapacidad Múltiple",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        )
        Text(
            text = "Únete como aliado, voluntario, comprador o donante",
            fontSize = 14.sp,
            color = colors.onSurfaceVariant
        )

        // Tipo de Colaboración (lista desplegable)
        Column {
            Text("Tipo de Colaboración *",
                fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = menuAbierto,
                onExpandedChange = { menuAbierto = it }
            ) {
                OutlinedTextField(
                    value = estado.tipo?.etiqueta ?: "Selecciona una opción",
                    onValueChange = {},
                    readOnly = true,
                    isError = estado.errores.tipo != null,
                    supportingText = {
                        if (estado.errores.tipo != null) {
                            Text(estado.errores.tipo!!, color = colors.error)
                        }
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAbierto) },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuAbierto,
                    onDismissRequest = { menuAbierto = false }
                ) {
                    TipoColaboracion.opciones().forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion.etiqueta) },
                            onClick = {
                                viewModel.onTipoChange(opcion)
                                menuAbierto = false
                            }
                        )
                    }
                }
            }
        }


        AnimatedVisibility(visible = estado.tipo == TipoColaboracion.Donante) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.surfaceVariant, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.outline, RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                CampoTexto(
                    etiqueta = "Ingresa el monto que quieres donar *",
                    valor = estado.monto,
                    placeholder = "Mínimo de 500 CLP",
                    error = estado.errores.monto,
                    onCambio = { viewModel.onMontoChange(it) },
                    tipoTeclado = KeyboardType.Number
                )
            }
        }
        // Resumen del carrito: solo si eligió "Compra de Productos Alba Lab"
        AnimatedVisibility(visible = estado.tipo == TipoColaboracion.Comprador) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(colors.surfaceVariant, RoundedCornerShape(12.dp))
                    .border(1.dp, colors.outline, RoundedCornerShape(12.dp))
                    .clickable {onVerPedido()}
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Resumen de tu compra", fontWeight = FontWeight.SemiBold, color = colors.onSurface)

                if (carrito.isEmpty()) {
                    Text(
                        "Tu carrito está vacío. Agrega productos desde el Catálogo.",
                        fontSize = 13.sp,
                        color = colors.onSurfaceVariant
                    )
                } else {
                    carrito.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "${item.cantidad} × ${item.producto.nombre}",
                                fontSize = 13.sp,
                                color = colors.onSurfaceVariant,
                                modifier = Modifier.weight(1f)
                            )
                            Text(formatearPesos(item.subtotal), fontSize = 13.sp, color = colors.onSurface)
                        }
                    }
                    HorizontalDivider(color = colors.outline)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("TOTAL", fontWeight = FontWeight.Bold, color = colors.onSurface, modifier = Modifier.weight(1f))
                        Text(formatearPesos(total), fontWeight = FontWeight.Bold, color = colors.onPrimaryContainer)
                    }
                }
            }
        }

        // Nombres-datos
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            CampoTexto(
                etiqueta = "Nombre *",
                valor = estado.nombre,
                placeholder = "Juan",
                error = estado.errores.nombre,
                onCambio = { viewModel.onNombreChange(it) },
                modifier = Modifier.weight(1f)
            )
            CampoTexto(
                etiqueta = "Apellidos *",
                valor = estado.apellidos,
                placeholder = "Pérez",
                error = estado.errores.apellidos,
                onCambio = { viewModel.onApellidosChange(it) },
                modifier = Modifier.weight(1f)
            )
        }

        CampoTexto(
            etiqueta = "Correo electrónico *",
            valor = estado.correo,
            placeholder = "ejemplo@correo.cl",
            error = estado.errores.correo,
            onCambio = { viewModel.onCorreoChange(it) },
            tipoTeclado = KeyboardType.Email
        )

        CampoTexto(
            etiqueta = "Confirmar correo electrónico *",
            valor = estado.confirmarCorreo,
            placeholder = "ejemplo@correo.cl",
            error = estado.errores.confirmarCorreo,
            onCambio = { viewModel.onConfirmarCorreoChange(it) },
            tipoTeclado = KeyboardType.Email
        )

        CampoTexto(
            etiqueta = "Teléfono de contacto *",
            valor = estado.telefono,
            placeholder = "+56 9 1234 5678",
            error = estado.errores.telefono,
            onCambio = { viewModel.onTelefonoChange(it) },
            tipoTeclado = KeyboardType.Phone
        )

        CampoTexto(
            etiqueta = "Mensaje o detalle",
            valor = estado.mensaje,
            placeholder = "Escribe tu mensaje o consulta...",
            error = null,
            onCambio = { viewModel.onMensajeChange(it) }
        )

        // Botón enviar: muestra un loader mientras envía
        Button(
            onClick = {
                val esCompra = estado.tipo == TipoColaboracion.Comprador
                    viewModel.enviarRegistro(totalCarrito = total){
                        if(esCompra) carritoViewModel.vaciar()
                        onRegistroExitoso()
                    }
                },
            enabled = !estado.enviando,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (estado.enviando) {
                CircularProgressIndicator(
                    color = colors.onPrimary,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text("Hazte socio",fontSize = 15.sp, fontWeight = FontWeight.Bold, color = colors.onPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = colors.onPrimary)
            }
        }
    }
}

@Composable
fun CampoTexto(
    etiqueta: String,
    valor: String,
    placeholder: String,
    error: String?,
    onCambio: (String) -> Unit,
    modifier: Modifier = Modifier,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier) {
        Text(etiqueta, fontSize =12.sp, fontWeight = FontWeight.SemiBold,)
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = onCambio,
            textStyle = TextStyle(fontSize = 16.sp),
            placeholder = { Text(placeholder, fontSize = 12.sp,) },
            isError = error != null,
            supportingText = {
                if (error != null) {
                    Text(error, fontSize = 13.sp, color = MaterialTheme.colorScheme.error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            shape = RoundedCornerShape(8.dp),
            colors = coloresCampo(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Colores de TODOS los campos del formulario
@Composable
fun coloresCampo(): TextFieldColors {
    val colors = MaterialTheme.colorScheme
    return OutlinedTextFieldDefaults.colors(
        // Lo que escribe o selecciona el usuario → OSCURO
        focusedTextColor = colors.onSurface,
        unfocusedTextColor = colors.onSurface,
        errorTextColor = colors.onSurface,

        // Textos de ejemplo (placeholder) → CLARITO
        focusedPlaceholderColor = colors.onSurfaceVariant,
        unfocusedPlaceholderColor = colors.onSurfaceVariant,
        errorPlaceholderColor = colors.onSurfaceVariant,

        // Bordes, cursor y fondo
        focusedBorderColor = colors.primary,
        unfocusedBorderColor = colors.outlineVariant,
        errorBorderColor = colors.error,
        cursorColor = colors.primary,
        focusedContainerColor = colors.surface,
        unfocusedContainerColor = colors.surface,
        errorContainerColor = colors.surface
    )
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun RegistroScreenPreview() {
    Corpaliv_Grupo9Theme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                RegistroScreen(viewModel = RegistroViewModel(),
                    carritoViewModel = CarritoViewModel(),
                    onRegistroExitoso = {},
                    onVerPedido = {}
                )
            }
        }
    }
}