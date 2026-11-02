package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.model.InfoImpactMetric
import cl.duoc.corpaliv_grupo9.ui.theme.InfoImpactNavy
import cl.duoc.corpaliv_grupo9.ui.theme.InfoImpactViolet
import cl.duoc.corpaliv_grupo9.ui.theme.White

@Composable
fun InfoImpactSection(
    lead: InfoImpactMetric,
    metrics: List<InfoImpactMetric>
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        InfoSectionHeading(eyebrow = "Impacto", title = "Resultados que se ven")
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(145.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = InfoImpactNavy),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val centerX = size.width * 0.86f
                    val centerY = size.height * 0.55f
                    drawCircle(
                        color = White.copy(alpha = 0.12f),
                        radius = size.height * 0.62f,
                        center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                        style = Stroke(width = 1.dp.toPx())
                    )
                    drawCircle(
                        color = White.copy(alpha = 0.12f),
                        radius = size.height * 0.39f,
                        center = androidx.compose.ui.geometry.Offset(centerX, centerY),
                        style = Stroke(width = 1.dp.toPx())
                    )
                    drawCircle(
                        color = InfoImpactViolet.copy(alpha = 0.6f),
                        radius = size.height * 0.22f,
                        center = androidx.compose.ui.geometry.Offset(centerX, centerY)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            lead.icon,
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(9.dp))
                        Text(
                            text = lead.label,
                            color = White.copy(alpha = 0.9f),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Text(
                        text = lead.value,
                        color = White,
                        fontSize = 58.sp,
                        lineHeight = 62.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            metrics.forEachIndexed { index, metric ->
                val accent = if (index == 0) Color(0xFF7428E4) else Color(0xFF0D70D2)
                InfoMetricCard(
                    metric = metric,
                    accent = accent,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun InfoMetricCard(
    metric: InfoImpactMetric,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(150.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = accent),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = metric.icon,
                contentDescription = null,
                tint = White.copy(alpha = 0.16f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 12.dp)
                    .size(100.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 15.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = metric.label,
                    color = White,
                    fontSize = 13.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(0.82f),
                    maxLines = 2
                )
                Column {
                    Text(
                        text = metric.value,
                        color = White,
                        fontSize = 48.sp,
                        lineHeight = 50.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}
