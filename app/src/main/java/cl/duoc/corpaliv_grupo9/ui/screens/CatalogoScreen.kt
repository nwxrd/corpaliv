package cl.duoc.corpaliv_grupo9.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import cl.duoc.corpaliv_grupo9.model.productosAlbaLab
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.model.Producto
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.model.ItemCarrito
import cl.duoc.corpaliv_grupo9.model.formatearPesos
import cl.duoc.corpaliv_grupo9.model.totalCarrito
import cl.duoc.corpaliv_grupo9.ui.theme.Corpaliv_Grupo9Theme
import cl.duoc.corpaliv_grupo9.viewmodel.CarritoViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogoScreen(
    carritoViewModel: CarritoViewModel,
    onFinalizarCompra: () -> Unit
) {
    val carrito by carritoViewModel.items.collectAsState()
    val colors = MaterialTheme.colorScheme
    var hojaAbierta by rememberSaveable { mutableStateOf(false) }
    val unidades = carrito.sumOf { it.cantidad }

    LaunchedEffect(carrito.isEmpty()) {
        if (carrito.isEmpty()) hojaAbierta = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Productos elaborados en nuestros talleres",
                    fontSize = 14.sp,
                    color = colors.onSurfaceVariant
                )
            }
            items(productosAlbaLab, key = { it.id }) { producto ->
                ProductoCard(
                    producto = producto,
                    onAgregar = { cantidad -> carritoViewModel.agregar(producto, cantidad) }
                )
            }
        }

        if (carrito.isNotEmpty()) {
            Spacer(Modifier.height(10.dp))
            BarraPedido(
                unidades = unidades,
                total = formatearPesos(totalCarrito(carrito)),
                onResumen = { hojaAbierta = true },
                onContinuar = onFinalizarCompra
            )
        }
    }

    if (hojaAbierta) {
        ModalBottomSheet(onDismissRequest = { hojaAbierta = false }) {
            MenuCarrito(
                carrito = carrito,
                onQuitar = { carritoViewModel.quitar(it) },
                onVaciar = { carritoViewModel.vaciar() },
                onFinalizar = {
                    hojaAbierta = false
                    onFinalizarCompra()
                }
            )
        }
    }
}

@Composable
private fun BarraPedido(
    unidades: Int,
    total: String,
    onResumen: () -> Unit,
    onContinuar: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val resumen = if (unidades == 1) "1 producto · $total" else "$unidades productos · $total"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surfaceVariant)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = colors.onSurface
            )
            Spacer(Modifier.width(8.dp))
            Text(
                resumen,
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.SemiBold,
                color = colors.onSurface
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TextButton(
                onClick = onResumen,
                modifier = Modifier.weight(1f)
            ) {
                Text("Ver pedido")
            }
            Button(
                onClick = onContinuar,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary)
            ) {
                Text("Continuar", fontWeight = FontWeight.Bold, color = colors.onPrimary)
            }
        }
    }
}

// Contenido de la hoja del carrito
@Composable
fun MenuCarrito(
    carrito: List<ItemCarrito>,
    onQuitar: (Producto) -> Unit,
    onVaciar: () -> Unit,
    onFinalizar: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Tu pedido",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        )
        Spacer(Modifier.height(12.dp))

        if (carrito.isEmpty()) {
            Text("Tu pedido está vacío", color = colors.onSurfaceVariant)
        } else {
            carrito.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (item.producto.imagen != null) {
                        Image(
                            painter = painterResource(id = item.producto.imagen),
                            contentDescription = item.producto.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(item.producto.nombre, fontSize = 13.sp, color = colors.onSurface)
                        Text(
                            "${item.cantidad} × ${formatearPesos(item.producto.valor)}",
                            fontSize = 12.sp,
                            color = colors.onSurfaceVariant
                        )
                    }
                    TextButton(onClick = { onQuitar(item.producto) }) {
                        Text("Quitar")
                    }
                }
            }

            HorizontalDivider(color = colors.outline, modifier = Modifier.padding(vertical = 8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text("SUBTOTAL:", fontSize = 13.sp, color = colors.onSurfaceVariant, modifier = Modifier.weight(1f))
                Text(formatearPesos(totalCarrito(carrito)), fontWeight = FontWeight.Bold, color = colors.onSurface)
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = onVaciar,
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Vaciar pedido", color = colors.onSurfaceVariant)
            }
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = onFinalizar,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar", fontWeight = FontWeight.Bold, color = colors.onPrimary)
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: Producto, onAgregar: (Int) -> Unit) {
    // Cantidad elegida en el selector – 1 +
    var cantidad by rememberSaveable { mutableStateOf(1) }
    val colors = MaterialTheme.colorScheme

    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, colors.outline),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp)
                .background(colors.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            if (producto.imagen != null) {
                Image(
                    painter = painterResource(id = producto.imagen),
                    contentDescription = producto.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Icon(
                    producto.icono,
                    contentDescription = producto.nombre,
                    tint = colors.primary,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Column(Modifier.padding(10.dp)) {
            Text(producto.nombre, fontSize = 13.sp,
                fontWeight = FontWeight.Bold, color = colors.onSurface,maxLines=1,
                overflow = TextOverflow.Ellipsis)
            Text(producto.descripcion, fontSize = 11.sp,
                color = colors.onSurfaceVariant,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(6.dp))
            Text(formatearPesos(producto.valor), fontSize = 13.sp, fontWeight = FontWeight.Black, color = colors.onPrimaryContainer)
            Spacer(Modifier.height(8.dp))

            // Selector de cantidad – 1 +
            SelectorCantidad(
                cantidad = cantidad,
                onMenos = { if (cantidad > 1) cantidad-- },
                onMas = { cantidad++ }
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    onAgregar(cantidad)
                    cantidad = 1   // vuelve a 1 después de agregar
                },
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
            ) {
                Text("AÑADIR AL CARRITO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = colors.onPrimary, maxLines = 1)
            }
        }
    }
}

// Selector – 1 + como el de la página web
@Composable
fun SelectorCantidad(cantidad: Int, onMenos: () -> Unit, onMas: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = Modifier.border(1.dp, colors.outline),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable { onMenos() },
            contentAlignment = Alignment.Center
        ) {
            Text("–", color = colors.onSurfaceVariant)
        }
        Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
            Text("$cantidad", color = colors.onSurface)
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable { onMas() },
            contentAlignment = Alignment.Center
        ) {
            Text("+", color = colors.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    Corpaliv_Grupo9Theme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                CatalogoScreen(carritoViewModel = CarritoViewModel(), onFinalizarCompra = {})
            }
        }
    }
}