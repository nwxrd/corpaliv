package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import cl.duoc.corpaliv_grupo9.ui.theme.BluePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.White
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroEnd
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroStart

@Composable
fun InfoHeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(Brush.linearGradient(listOf(InfoHeroStart, InfoHeroEnd)))
    ) {
        AsyncImage(
            model = "https://images.unsplash.com/photo-1522202176988-66273c2fd55f?auto=format&fit=crop&w=1400&q=85",
            contentDescription = "Personas colaborando alrededor de una mesa",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to InfoHeroStart.copy(alpha = 0f),
                        0.2f to InfoHeroStart.copy(alpha = 0.08f),
                        0.38f to InfoHeroStart.copy(alpha = 0.3f),
                        0.56f to InfoHeroStart.copy(alpha = 0.68f),
                        0.78f to InfoHeroStart.copy(alpha = 0.9f),
                        1f to InfoHeroEnd.copy(alpha = 0.96f)
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp, top = 28.dp, end = 24.dp, bottom = 48.dp)
        ) {
            Text(
                text = "QUIÉNES SOMOS",
                color = White,
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
    val colors = MaterialTheme.colorScheme

    Column(modifier = modifier) {
        Text(
            text = eyebrow.uppercase(),
            color = colors.primary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = title,
            color = colors.onSurface,
            fontSize = 25.sp,
            lineHeight = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
fun InfoJoinSection(
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BluePrimary),
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
                colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = BluePrimary),
                contentPadding = PaddingValues(start = 18.dp, end = 7.dp)
            ) {
                Text("Ir al registro", fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(BluePrimary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = White)
                }
            }
        }
    }
}
