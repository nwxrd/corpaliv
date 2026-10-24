package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import cl.duoc.corpaliv_grupo9.ui.theme.AlbaColors
import cl.duoc.corpaliv_grupo9.viewmodel.RegistroViewModel
import androidx.compose.material3.HorizontalDivider
import cl.duoc.corpaliv_grupo9.model.formatearPesos
import cl.duoc.corpaliv_grupo9.model.totalCarrito
import cl.duoc.corpaliv_grupo9.viewmodel.CarritoViewModel
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.text.TextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    carritoViewModel: CarritoViewModel,
    onRegistroExitoso: () -> Unit
) {

    val estado by viewModel.formulario.collectAsState()
    val carrito by carritoViewModel.items.collectAsState()
    val total = totalCarrito(carrito)
    var menuAbierto by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AlbaColors.Blanco)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Encabezado
        Text(
            text = "¡Eres genial! Gracias por tu compromiso con la Discapacidad Múltiple",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AlbaColors.Texto
        )
        Text(
            text = "Únete como aliado, voluntario, comprador o donante",
            fontSize = 14.sp,
            color = AlbaColors.TextoSecundario
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
                            Text(estado.errores.tipo!!, color = AlbaColors.Error)
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
                    .background(AlbaColors.Superficie, RoundedCornerShape(12.dp))
                    .border(1.dp, AlbaColors.Borde, RoundedCornerShape(12.dp))
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
        // Resumen del carrito: solo si eligió "Compra de Productos Alba Lab" (Guía 12)
        AnimatedVisibility(visible = estado.tipo == TipoColaboracion.Comprador) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AlbaColors.Superficie, RoundedCornerShape(12.dp))
                    .border(1.dp, AlbaColors.Borde, RoundedCornerShape(12.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Resumen de tu compra", fontWeight = FontWeight.SemiBold, color = AlbaColors.Texto)

                if (carrito.isEmpty()) {
                    Text(
                        "Tu carrito está vacío. Agrega productos desde el Catálogo.",
                        fontSize = 13.sp,
                        color = AlbaColors.TextoSecundario
                    )
                } else {
                    carrito.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "${item.cantidad} × ${item.producto.nombre}",
                                fontSize = 13.sp,
                                color = AlbaColors.TextoMedio,
                                modifier = Modifier.weight(1f)
                            )
                            Text(formatearPesos(item.subtotal), fontSize = 13.sp, color = AlbaColors.Texto)
                        }
                    }
                    HorizontalDivider(color = AlbaColors.Borde)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text("TOTAL", fontWeight = FontWeight.Bold, color = AlbaColors.Texto, modifier = Modifier.weight(1f))
                        Text(formatearPesos(total), fontWeight = FontWeight.Bold, color = AlbaColors.Precio)
                    }
                }
            }
        }

        // Nombre y apellidos lado a lado
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
            colors = ButtonDefaults.buttonColors(containerColor = AlbaColors.Azul),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (estado.enviando) {
                CircularProgressIndicator(
                    color = AlbaColors.Blanco,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Text("Hazte socio",fontSize = 15.sp, fontWeight = FontWeight.Bold, color = AlbaColors.Blanco)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, tint = AlbaColors.Blanco)
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
                    Text(error, fontSize = 13.sp, color = AlbaColors.Error)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado),
            shape = RoundedCornerShape(8.dp),
            colors = coloresCampo(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Colores de TODOS los campos del formulario (salen de AlbaColors)
@Composable
fun coloresCampo(): TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        // Lo que escribe o selecciona el usuario → OSCURO
        focusedTextColor = AlbaColors.Texto,
        unfocusedTextColor = AlbaColors.Texto,
        errorTextColor = AlbaColors.Texto,

        // Textos de ejemplo (placeholder) → CLARITO
        focusedPlaceholderColor = AlbaColors.Placeholder,
        unfocusedPlaceholderColor = AlbaColors.Placeholder,
        errorPlaceholderColor = AlbaColors.Placeholder,

        // Bordes, cursor y fondo
        focusedBorderColor = AlbaColors.Azul,
        unfocusedBorderColor = AlbaColors.BordeCampo,
        errorBorderColor = AlbaColors.Error,
        cursorColor = AlbaColors.Azul,
        focusedContainerColor = AlbaColors.Blanco,
        unfocusedContainerColor = AlbaColors.Blanco,
        errorContainerColor = AlbaColors.Blanco
    )
}

@Preview(showBackground = true, heightDp = 1200)
@Composable
fun RegistroScreenPreview() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            RegistroScreen(viewModel = RegistroViewModel(),
                carritoViewModel = CarritoViewModel(),
                onRegistroExitoso = {}
            )
        }
    }
}