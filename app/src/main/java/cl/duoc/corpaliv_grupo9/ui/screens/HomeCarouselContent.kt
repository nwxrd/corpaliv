package cl.duoc.corpaliv_grupo9.ui.screens

import cl.duoc.corpaliv_grupo9.model.CarouselItem
import cl.duoc.corpaliv_grupo9.ui.theme.BlueOnContainer
import cl.duoc.corpaliv_grupo9.ui.theme.BluePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.Green100
import cl.duoc.corpaliv_grupo9.ui.theme.Green600
import cl.duoc.corpaliv_grupo9.ui.theme.Green950
import cl.duoc.corpaliv_grupo9.ui.theme.Slate900
import cl.duoc.corpaliv_grupo9.ui.theme.Slate950
import cl.duoc.corpaliv_grupo9.ui.theme.White

fun homeCarouselItems(
    onNavigateToCatalog: () -> Unit,
    onNavigateToInfo: () -> Unit,
    onNavigateToForm: () -> Unit
): List<CarouselItem> = listOf(
    CarouselItem(
        tag = "Inclusión Laboral",
        tagBgColor = Green600,
        tagTextColor = White,
        title = "Impulsando la inclusión laboral",
        description = "Jóvenes confeccionando productos artesanales con sentido social.",
        buttonText = "Ver Catálogo",
        buttonBgColor = Green600,
        buttonTextColor = White,
        imageUrl = "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?auto=format&fit=crop&w=600&q=80",
        gradientStartColor = Slate950,
        onClick = onNavigateToCatalog
    ),
    CarouselItem(
        tag = "Escuela Especial",
        tagBgColor = BluePrimary,
        tagTextColor = White,
        title = "Educación con amor y dedicación",
        description = "Atención especializada a niños y jóvenes con multidiscapacidad.",
        buttonText = "Conocer Más",
        buttonBgColor = BluePrimary,
        buttonTextColor = White,
        imageUrl = "https://images.unsplash.com/photo-1577896851231-70ef18881754?auto=format&fit=crop&w=600&q=80",
        gradientStartColor = Slate900,
        onClick = onNavigateToInfo
    ),
    CarouselItem(
        tag = "Comunidad",
        tagBgColor = Green100,
        tagTextColor = BlueOnContainer,
        title = "Tu apoyo cambia vidas",
        description = "Súmate como voluntario o genera una alianza RSE con tu empresa.",
        buttonText = "Sumarme",
        buttonBgColor = Green100,
        buttonTextColor = BlueOnContainer,
        imageUrl = "https://images.unsplash.com/photo-1559027615-cd4628902d4a?auto=format&fit=crop&w=600&q=80",
        gradientStartColor = Green950,
        onClick = onNavigateToForm
    )
)