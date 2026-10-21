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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.ui.theme.AlbaLab600
import cl.duoc.corpaliv_grupo9.ui.theme.Brand700
import cl.duoc.corpaliv_grupo9.ui.theme.Slate200
import cl.duoc.corpaliv_grupo9.ui.theme.Slate50
import cl.duoc.corpaliv_grupo9.ui.theme.Slate500

import cl.duoc.corpaliv_grupo9.ui.theme.Slate800



@Composable
fun CatalogoScreen(onCotizar: (Producto)-> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text("CATALOGO ALBA LAB", fontSize=18.sp, fontWeight = FontWeight.Bold, color = Slate800)
        Text("Productos elaborados artesanalmente en nuestros talleres", fontSize = 12.sp, color = Slate500)

        Spacer(Modifier.height(6.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(productosAlbaLab ){producto ->
                ProductoCard(producto = producto, onCotizar = {onCotizar(producto)})
            }
        }

    }
}

@Composable
private fun ProductoCard(producto: Producto, onCotizar: () -> Unit) {
  Card(
      shape = RoundedCornerShape(6.dp),
      border = BorderStroke(1.dp, Slate200),
      colors = CardDefaults.cardColors(containerColor = Slate50)

  ){
      Box(
          modifier = Modifier
              .fillMaxWidth()
              .height(106.dp)
              .background(producto.colorFondo),
          contentAlignment = Alignment.Center
      ){
          if(producto.imagen !=null){
              Image(
                  painter = painterResource(id= producto.imagen),
                  contentDescription = producto.nombre,
                  contentScale = ContentScale.Crop,
                  modifier = Modifier.fillMaxSize()
              )
          } else{
              Icon(
                  producto.icono,
                  contentDescription = producto.nombre,
                  tint = producto.colorIcono,
                  modifier = Modifier.size(36.dp)
              )
          }
      }
      Column(Modifier.padding(10.dp)){
          Text(producto.nombre, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Slate800)
          Text(producto.descripcion, fontSize = 11.sp, color = Slate500)
          Spacer(Modifier.height(8.dp))
          Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
          ) {
              Text(producto.precio, fontSize = 13.sp,fontWeight= FontWeight.Black, color = Brand700)
              Button(
                  onClick = onCotizar,
                  shape = RoundedCornerShape(6.dp),
                  colors = ButtonDefaults.buttonColors(containerColor = AlbaLab600),
                  contentPadding = PaddingValues(horizontal = 10.dp),
                  modifier = Modifier.height(30.dp)
              ) {
                  Text("Cotizar",fontSize=11.sp)
              }
          }
      }
  }

}
@Preview(showBackground = true)
@Composable
fun CatalogoPreview() {
    CatalogoScreen(onCotizar = {})
}
