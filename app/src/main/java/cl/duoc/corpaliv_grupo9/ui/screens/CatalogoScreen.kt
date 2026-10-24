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
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
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
import cl.duoc.corpaliv_grupo9.ui.theme.AlbaColors
import cl.duoc.corpaliv_grupo9.viewmodel.CarritoViewModel



@Composable
fun CatalogoScreen(
    carritoViewModel: CarritoViewModel,
    onFinalizarCompra: () -> Unit
) {
    // Carrito desde el ViewModel
    val carrito by carritoViewModel.items.collectAsState()

    // Si el menú del carrito está abierto
    var menuCarritoAbierto by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AlbaColors.Blanco)
            .padding(16.dp)
    ) {
        // Encabezado con el botón del carrito a la derecha
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("CATÁLOGO ALBA LAB", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = AlbaColors.Texto)
                Text("Productos elaborados artesanalmente en nuestros talleres", fontSize = 12.sp, color = AlbaColors.TextoSecundario)
            }

            Box {
                // Botón: ícono del carrito + total
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { menuCarritoAbierto = true }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.ShoppingCart, contentDescription = "Carrito", tint = AlbaColors.Texto)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(formatearPesos(totalCarrito(carrito)), fontWeight = FontWeight.Bold, color = AlbaColors.Texto)
                }

                // Menú desplegable del carrito
                DropdownMenu(
                    expanded = menuCarritoAbierto,
                    onDismissRequest = { menuCarritoAbierto = false },
                    modifier = Modifier
                        .width(300.dp)
                        .background(AlbaColors.Blanco)
                ) {
                    MenuCarrito(
                        carrito = carrito,
                        onQuitar = { carritoViewModel.quitar(it) },
                        onVaciar = { carritoViewModel.vaciar() },
                        onFinalizar = {
                            menuCarritoAbierto = false
                            onFinalizarCompra()
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(productosAlbaLab, key = { it.id }) { producto ->
                ProductoCard(
                    producto = producto,
                    onAgregar = { cantidad -> carritoViewModel.agregar(producto, cantidad) }
                )
            }
        }
    }
}

// Contenido del menú desplegable del carrito
@Composable
fun MenuCarrito(
    carrito: List<ItemCarrito>,
    onQuitar: (Producto) -> Unit,
    onVaciar: () -> Unit,
    onFinalizar: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (carrito.isEmpty()) {
            Text("Tu carrito está vacío", color = AlbaColors.TextoSecundario)
        } else {
            // Productos del carrito
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
                        Text(item.producto.nombre, fontSize = 13.sp, color = AlbaColors.Texto)
                        Text(
                            "${item.cantidad} × ${formatearPesos(item.producto.valor)}",
                            fontSize = 12.sp,
                            color = AlbaColors.TextoSecundario
                        )
                    }
                    IconButton(
                        onClick = { onQuitar(item.producto) },
                        modifier = Modifier
                            .size(32.dp)
                            .border(1.dp, AlbaColors.Borde, CircleShape)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = "Quitar", tint = AlbaColors.TextoSecundario, modifier = Modifier.size(16.dp))
                    }
                }
            }

            HorizontalDivider(color = AlbaColors.Borde, modifier = Modifier.padding(vertical = 8.dp))

            // Subtotal
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("SUBTOTAL:", fontSize = 13.sp, color = AlbaColors.TextoSecundario, modifier = Modifier.weight(1f))
                Text(formatearPesos(totalCarrito(carrito)), fontWeight = FontWeight.Bold, color = AlbaColors.Texto)
            }

            HorizontalDivider(color = AlbaColors.Borde, modifier = Modifier.padding(vertical = 8.dp))

            // Botones
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(
                    onClick = onVaciar,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Vaciar carrito", fontSize = 11.sp, color = AlbaColors.TextoSecundario)
                }
                Button(
                    onClick = onFinalizar,
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AlbaColors.Rojo),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Comprar", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = AlbaColors.Blanco)
                }
            }
        }
    }
}

@Composable
private fun ProductoCard(producto: Producto, onAgregar: (Int) -> Unit) {
    // Cantidad elegida en el selector – 1 +
    var cantidad by rememberSaveable { mutableStateOf(1) }

    Card(
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, AlbaColors.Borde),
        colors = CardDefaults.cardColors(containerColor = AlbaColors.Superficie)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(106.dp)
                .background(producto.colorFondo),
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
                    tint = producto.colorIcono,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
        Column(Modifier.padding(10.dp)) {
            Text(producto.nombre, fontSize = 13.sp,
                fontWeight = FontWeight.Bold, color = AlbaColors.Texto,maxLines=1,
                overflow = TextOverflow.Ellipsis)
            Text(producto.descripcion, fontSize = 11.sp,
                color = AlbaColors.TextoSecundario,
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis)
            Spacer(Modifier.height(6.dp))
            Text(producto.precio, fontSize = 13.sp, fontWeight = FontWeight.Black, color = AlbaColors.Precio)
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
                colors = ButtonDefaults.buttonColors(containerColor = AlbaColors.Rojo),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(34.dp)
            ) {
                Text("AÑADIR AL CARRITO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AlbaColors.Blanco, maxLines = 1)
            }
        }
    }
}

// Selector – 1 + como el de la página web
@Composable
fun SelectorCantidad(cantidad: Int, onMenos: () -> Unit, onMas: () -> Unit) {
    Row(
        modifier = Modifier.border(1.dp, AlbaColors.Borde),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable { onMenos() },
            contentAlignment = Alignment.Center
        ) {
            Text("–", color = AlbaColors.TextoSecundario)
        }
        Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
            Text("$cantidad", color = AlbaColors.Texto)
        }
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable { onMas() },
            contentAlignment = Alignment.Center
        ) {
            Text("+", color = AlbaColors.Teal)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            CatalogoScreen(carritoViewModel = CarritoViewModel(), onFinalizarCompra = {})
        }
    }
}