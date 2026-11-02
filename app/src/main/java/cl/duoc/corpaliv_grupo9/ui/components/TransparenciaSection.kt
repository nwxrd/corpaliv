package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.model.AporteImpacto
import cl.duoc.corpaliv_grupo9.navigation.Screen
import cl.duoc.corpaliv_grupo9.ui.theme.HomeIconViolet
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette

@Composable
fun TransparenciaSection(
    modifier: Modifier = Modifier,
    onItemClick: (String) -> Unit
) {
    val palette = corpalivSurfacePalette()

    val items = listOf(
        AporteImpacto(
            tag = "TU DONACIÓN",
            title = "Materiales y talleres de formación",
            description = "Financia insumos directos para el aprendizaje de jóvenes.",
            icon = Icons.Default.Favorite,
            iconBgColor = HomePrimary,
            destination = Screen.Registro.route
        ),
        AporteImpacto(
            tag = "TU COMPRA",
            title = "Producción del taller Alba Lab",
            description = "Sostiene el empleo e inclusión de los artesanos.",
            icon = Icons.Default.ShoppingBag,
            iconBgColor = HomeIconViolet,
            destination = Screen.Catalogo.route
        ),
        AporteImpacto(
            tag = "TU ALIANZA",
            title = "Programas de inclusión laboral",
            description = "Permite crear nuevos cupos en empresas asociadas.",
            icon = Icons.Default.Handshake,
            iconBgColor = HomePrimary,
            destination = Screen.Registro.route
        )
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "TRANSPARENCIA",
                color = palette.primary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.1.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "¿A dónde va tu aporte?",
                color = palette.text,
                fontSize = 22.sp,
                lineHeight = 27.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Conoce el impacto detrás de cada forma de colaborar.",
                color = palette.secondaryText,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items.forEachIndexed { index, item ->
                ImpactCard(
                    item = item,
                    headerColor = when (index) {
                        0 -> palette.cardBlue
                        1 -> palette.cardViolet
                        else -> palette.cardVioletLight
                    },
                    onClick = { onItemClick(item.destination) }
                )
            }
        }
    }
}

@Composable
private fun ImpactCard(
    item: AporteImpacto,
    headerColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    val palette = corpalivSurfacePalette()

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .width(252.dp)
            .height(264.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = palette.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(92.dp)
                    .background(headerColor)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val upperSweep = Path().apply {
                        moveTo(size.width * 0.62f, 0f)
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        lineTo(size.width * 0.84f, size.height)
                        cubicTo(
                            size.width * 0.8f,
                            size.height * 0.56f,
                            size.width * 0.68f,
                            size.height * 0.3f,
                            size.width * 0.62f,
                            0f
                        )
                        close()
                    }
                    drawPath(upperSweep, color = White.copy(alpha = 0.42f))

                    val lowerFacet = Path().apply {
                        moveTo(0f, size.height * 0.72f)
                        lineTo(size.width * 0.28f, size.height * 0.56f)
                        lineTo(size.width * 0.48f, size.height)
                        lineTo(0f, size.height)
                        close()
                    }
                    drawPath(lowerFacet, color = HomePrimary.copy(alpha = 0.08f))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(item.iconBgColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(23.dp)
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = White
                    ) {
                        Text(
                            text = item.tag,
                            color = palette.primary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.3.sp,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                Text(
                    text = item.title,
                    color = palette.text,
                    fontSize = 18.sp,
                    lineHeight = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.description,
                    color = palette.secondaryText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ver impacto",
                        color = palette.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                            .background(HomePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}