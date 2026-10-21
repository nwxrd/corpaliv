package cl.duoc.corpaliv_grupo9.model

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import cl.duoc.corpaliv_grupo9.R


data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: String,
    val icono: ImageVector,
    val colorFondo: Color,
    val colorIcono: Color,
    @DrawableRes val imagen: Int?= null
)
    val productosAlbaLab = listOf(
        Producto(1,"Pack sal de Cahuil","Pack 4 sales de cahuil miniatura",
            "$10.990",Icons.Filled.Restaurant,Color(0xFFCCFBF1),
            Color(0xFF0D9488), imagen =R.drawable.sal_corpaliv),
        Producto(2,"Guatero de semillas","Ingredientes naturales","$11.990",
            Icons.Filled.LocalFireDepartment, Color(0xFFFEF3C7), Color(0xFFD97706),
            imagen=R.drawable.guatero_corpaliv),
        Producto(3,"Difusor Colgante","Aromatizante de 8ml","$7.500",
            Icons.Filled.Spa, Color(0xFFFEF3C7), Color(0xFFD97706),
            imagen=R.drawable.difusor_colgante),
        Producto(4,"Sales de Baño","Sales relajantes","$17.990",
            Icons.Filled.Spa, Color(0xFFFEF3C7), Color(0xFFD97706),
            imagen=R.drawable.sal_bano),




    )
