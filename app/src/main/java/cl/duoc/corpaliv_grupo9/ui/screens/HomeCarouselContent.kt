package cl.duoc.corpaliv_grupo9.ui.screens

import cl.duoc.corpaliv_grupo9.model.CarouselItem
import cl.duoc.corpaliv_grupo9.navigation.Screen

fun homeCarouselItems(): List<CarouselItem> = listOf(
    CarouselItem(
        tag = "Inclusión Laboral",
        title = "Impulsando la inclusión laboral",
        description = "Jóvenes confeccionando productos artesanales con sentido social.",
        buttonText = "Ver Catálogo",
        imageUrl = "https://images.unsplash.com/photo-1531482615713-2afd69097998?auto=format&fit=crop&w=900&q=85",
        destination = Screen.Catalogo.route
    ),
    CarouselItem(
        tag = "Escuela Especial",
        title = "Educación con amor y dedicación",
        description = "Atención especializada a niños y jóvenes con multidiscapacidad.",
        buttonText = "Conocer Más",
        imageUrl = "https://images.unsplash.com/photo-1509062522246-3755977927d7?auto=format&fit=crop&w=900&q=85",
        destination = Screen.Info.route
    ),
    CarouselItem(
        tag = "Comunidad",
        title = "Tu apoyo cambia vidas",
        description = "Súmate como voluntario o genera una alianza RSE con tu empresa.",
        buttonText = "Sumarme",
        imageUrl = "https://images.unsplash.com/photo-1517486808906-6ca8b3f04846?auto=format&fit=crop&w=900&q=85",
        destination = Screen.Registro.route
    )
)