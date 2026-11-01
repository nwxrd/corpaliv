package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import cl.duoc.corpaliv_grupo9.ui.theme.HomeBackground
import cl.duoc.corpaliv_grupo9.ui.theme.White

@Composable
fun HomeScreen(
    onNavigateToCatalog: () -> Unit = {},
    onNavigateToInfo: () -> Unit = {},
    onNavigateToForm: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .height(44.dp)
                .background(
                    Brush.verticalGradient(listOf(White, HomeBackground))
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeroCarousel(
                items = homeCarouselItems(onNavigateToCatalog, onNavigateToInfo, onNavigateToForm),
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            ColaboraSection(
                onOptionClick = { opcion ->
                    when (opcion) {
                        "Alba Lab", "Ver Catálogo" -> onNavigateToCatalog()
                        "Capacitaciones", "Voluntariado" -> onNavigateToInfo()
                        "Alianza RSE", "Donar Empresa", "Donar Persona" -> onNavigateToForm()
                    }
                }
            )

            TransparenciaSection(
                onItemClick = { item ->
                    when (item.tag) {
                        "TU COMPRA" -> onNavigateToCatalog()
                        "TU DONACIÓN", "TU ALIANZA" -> onNavigateToForm()
                        else -> onNavigateToInfo()
                    }
                }
            )
        }
    }
}