package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import cl.duoc.corpaliv_grupo9.model.InfoImpactMetric
import cl.duoc.corpaliv_grupo9.model.InfoProgram
import cl.duoc.corpaliv_grupo9.model.InfoProgramType
import cl.duoc.corpaliv_grupo9.model.InfoStory
import cl.duoc.corpaliv_grupo9.ui.theme.HomeText
import cl.duoc.corpaliv_grupo9.ui.theme.HomeTextSecondary
import cl.duoc.corpaliv_grupo9.ui.theme.InfoCtaEnd
import cl.duoc.corpaliv_grupo9.ui.theme.InfoCtaStart
import cl.duoc.corpaliv_grupo9.ui.theme.InfoEducationSurface
import cl.duoc.corpaliv_grupo9.ui.theme.InfoEmploymentSurface
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroEnd
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroScrim
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroStart
import cl.duoc.corpaliv_grupo9.ui.theme.InfoImpactNavy
import cl.duoc.corpaliv_grupo9.ui.theme.InfoImpactSky
import cl.duoc.corpaliv_grupo9.ui.theme.InfoImpactViolet
import cl.duoc.corpaliv_grupo9.ui.theme.InfoStorySurface
import cl.duoc.corpaliv_grupo9.ui.theme.HomeOutline
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White

@Composable
fun InfoHeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(410.dp)
            .background(Brush.linearGradient(listOf(InfoHeroStart, InfoHeroEnd)))
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1513364776144-60967b0f800f?auto=format&fit=crop&w=1400&q=85",
            contentDescription = "Manos trabajando en un taller creativo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to InfoHeroStart.copy(alpha = 0.08f),
                        0.36f to InfoHeroStart.copy(alpha = 0.12f),
                        0.58f to InfoHeroStart.copy(alpha = 0.48f),
                        0.82f to InfoHeroStart.copy(alpha = 0.96f),
                        1f to InfoHeroEnd
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            Text(
                text = "QUIÉNES SOMOS",
                color = Color(0xFF9DE9E2),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.2.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Corpaliv y Alba Lab",
                color = White,
                fontSize = 31.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Promovemos la autonomía, la participación y la inclusión social y laboral de personas con discapacidad múltiple.",
                color = White.copy(alpha = 0.94f),
                fontSize = 15.sp,
                lineHeight = 21.sp
            )
        }
    }
}

@Composable
fun InfoSectionHeading(
    eyebrow: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = eyebrow.uppercase(),
            color = HomePrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = title,
            color = HomeText,
            fontSize = 25.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun InfoProgramsSection(
    programs: List<InfoProgram>,
    onProgramClick: (InfoProgram) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        InfoSectionHeading(
            eyebrow = "Programas",
            title = "Formación y trabajo, de la mano"
        )
        programs.forEach { program ->
            InfoProgramCard(program = program, onClick = { onProgramClick(program) })
        }
    }
}

@Composable
private fun InfoProgramCard(
    program: InfoProgram,
    onClick: () -> Unit
) {
    val tint = when (program.type) {
        InfoProgramType.EDUCATION -> InfoEducationSurface
        InfoProgramType.EMPLOYMENT -> InfoEmploymentSurface
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(tint)
            ) {
                AsyncImage(
                    model = program.imageUrl,
                    contentDescription = program.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                0f to Color.Transparent,
                                0.7f to InfoHeroScrim.copy(alpha = 0.08f),
                                1f to InfoHeroScrim.copy(alpha = 0.58f)
                            )
                        )
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(14.dp),
                    shape = CircleShape,
                    color = White
                ) {
                    Text(
                        text = program.category,
                        color = HomePrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(HomePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = program.icon,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
                Text(
                    text = program.title,
                    color = HomeText,
                    fontSize = 19.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = program.description,
                    color = HomeTextSecondary,
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = onClick, contentPadding = ButtonDefaults.TextButtonContentPadding) {
                    Text("Conocer más", fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.width(6.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun InfoImpactSection(
    lead: InfoImpactMetric,
    metrics: List<InfoImpactMetric>
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        InfoSectionHeading(eyebrow = "Impacto", title = "Resultados que se ven")
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = InfoImpactNavy),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(lead.icon, contentDescription = null, tint = White, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(9.dp))
                    Text(
                        text = lead.label,
                        color = White.copy(alpha = 0.84f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = lead.value,
                    color = White,
                    fontSize = 64.sp,
                    lineHeight = 68.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            metrics.forEachIndexed { index, metric ->
                InfoMetricCard(
                    metric = metric,
                    accent = if (index == 0) InfoImpactViolet else InfoImpactSky,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Text(
            text = "Cifras de ejemplo. Se reemplazan por datos autorizados.",
            color = HomeTextSecondary,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    }
}

@Composable
private fun InfoMetricCard(
    metric: InfoImpactMetric,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(accent),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(metric.icon, contentDescription = null, tint = White, modifier = Modifier.size(21.dp))
                }
                metric.growth?.let { growth ->
                    Surface(shape = CircleShape, color = accent.copy(alpha = 0.12f)) {
                        Text(
                            text = growth,
                            color = accent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
                        )
                    }
                }
            }
            Text(
                text = metric.value,
                color = HomeText,
                fontSize = 34.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = metric.label,
                color = HomeTextSecondary,
                fontSize = 13.sp,
                lineHeight = 17.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun InfoStorySection(story: InfoStory) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        InfoSectionHeading(
            eyebrow = "Historias",
            title = "Una oportunidad que cambia el día a día"
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = InfoStorySurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .background(InfoEducationSurface)
                ) {
                    AsyncImage(
                        model = story.imageUrl,
                        contentDescription = "Manos trabajando en un taller de confección",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(14.dp),
                        shape = CircleShape,
                        color = White
                    ) {
                        Text(
                            text = story.category,
                            color = HomePrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                        )
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
                    Text(
                        text = "“${story.statement}”",
                        color = HomeText,
                        fontSize = 18.sp,
                        lineHeight = 25.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(Modifier.height(16.dp))
                    androidx.compose.material3.HorizontalDivider(color = HomeOutline)
                    Spacer(Modifier.height(12.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(InfoEducationSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, tint = HomePrimary)
                        }
                        Spacer(Modifier.width(11.dp))
                        Column {
                            Text(story.attribution, color = HomeText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text(story.note, color = HomeTextSecondary, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoJoinSection(onRegisterClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = InfoCtaStart),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Text(
                text = "¿Quieres colaborar?",
                color = White,
                fontSize = 23.sp,
                lineHeight = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(7.dp))
            Text(
                text = "Déjanos tus datos y el equipo de vinculación te contactará.",
                color = White.copy(alpha = 0.88f),
                fontSize = 14.sp,
                lineHeight = 19.sp
            )
            Spacer(Modifier.height(18.dp))
            Button(
                onClick = onRegisterClick,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = InfoCtaStart),
                contentPadding = PaddingValues(start = 18.dp, end = 7.dp)
            ) {
                Text("Ir al registro", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(InfoCtaEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = White)
                }
            }
        }
    }
}
