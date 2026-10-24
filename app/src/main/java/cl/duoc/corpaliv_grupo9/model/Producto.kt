package cl.duoc.corpaliv_grupo9.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.graphics.vector.ImageVector
import cl.duoc.corpaliv_grupo9.R


data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val valor: Int,
    val icono: ImageVector,
    @DrawableRes val imagen: Int?= null
)
    val productosAlbaLab = listOf(
        Producto(1,"Pack sal de Cahuil","Pack 4 sales de cahuil miniatura",
            10990,Icons.Filled.Restaurant
            , imagen =R.drawable.sal_corpaliv),
        Producto(2,"Guatero de semillas","Ingredientes naturales",11990,
            Icons.Filled.LocalFireDepartment,
            imagen=R.drawable.guatero_corpaliv),
        Producto(3,"Difusor Colgante","Aromatizante de 8ml",7500,
            Icons.Filled.Spa,
            imagen=R.drawable.difusor_colgante),
        Producto(4,"Sales de Baño","Sales relajantes",17990,
            Icons.Filled.Spa,
            imagen=R.drawable.sal_bano),
        Producto(5,"Bolsa Alba Lab","Bolsa ecologica de algodon medidas 40x60cm",4990,
            Icons.Filled.ShoppingBag,
            imagen=R.drawable.bolsa),
        Producto(6,"Farol de luz","Pequeño faro de luz decorativo",11990,
            Icons.Filled.Lightbulb,
            imagen=R.drawable.faro),




    )
