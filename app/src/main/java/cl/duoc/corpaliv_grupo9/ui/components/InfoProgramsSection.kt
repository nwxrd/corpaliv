package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import cl.duoc.corpaliv_grupo9.model.InfoProgram
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.InfoHeroScrim
import cl.duoc.corpaliv_grupo9.ui.theme.White
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette

@Composable
fun InfoProgramsSection(
    programs: List<InfoProgram>,
    onEmploymentClick: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        InfoSectionHeading(
            eyebrow = "Programas",
            title = "Formación y trabajo, de la mano"
        )
        programs.forEach { program ->
            InfoProgramCard(program = program, onEmploymentClick = onEmploymentClick)
        }
    }
}

@Composable
private fun InfoProgramCard(
    program: InfoProgram,
    onEmploymentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = corpalivSurfacePalette()
    val tint = if (program.isEmployment) palette.employmentSurface else palette.educationSurface

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = palette.surface),
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
                        color = palette.primary,
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
                    color = palette.text,
                    fontSize = 19.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    text = program.description,
                    color = palette.secondaryText,
                    fontSize = 14.sp,
                    lineHeight = 19.sp
                )
                if (program.isEmployment) {
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = onEmploymentClick,
                        contentPadding = ButtonDefaults.TextButtonContentPadding
                    ) {
                        Text("Ver catálogo", fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.width(6.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                    }
                }
            }
        }
    }
}
