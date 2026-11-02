package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.ui.components.ColaboraSection
import cl.duoc.corpaliv_grupo9.ui.components.HeroCarousel
import cl.duoc.corpaliv_grupo9.ui.components.TransparenciaSection
import cl.duoc.corpaliv_grupo9.ui.theme.corpalivSurfacePalette

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    val palette = corpalivSurfacePalette()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(44.dp)
                .background(
                    Brush.verticalGradient(listOf(palette.surface, palette.background))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 16.dp,
                    top = 12.dp,
                    end = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeroCarousel(items = homeCarouselItems(), onNavigate = onNavigate)
            ColaboraSection(onOptionClick = onNavigate)
            TransparenciaSection(onItemClick = onNavigate)
        }
    }
}