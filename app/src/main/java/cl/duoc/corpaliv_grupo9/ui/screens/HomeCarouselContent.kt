package cl.duoc.corpaliv_grupo9.ui.screens

import cl.duoc.corpaliv_grupo9.model.CarouselItem
import cl.duoc.corpaliv_grupo9.navigation.Screen

fun homeCarouselItems(): List<CarouselItem> = listOf(
    CarouselItem(
        tag = "Inclusión Laboral",
        title = "Impulsando la inclusión laboral",
        description = "Jóvenes confeccionando productos artesanales con sentido social.",
        buttonText = "Ver Catálogo",
        imageUrl = "https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?auto=format&fit=crop&w=600&q=80",
        destination = Screen.Catalogo.route
    ),
    CarouselItem(
        tag = "Escuela Especial",
        title = "Educación con amor y dedicación",
        description = "Atención especializada a niños y jóvenes con multidiscapacidad.",
        buttonText = "Conocer Más",
        imageUrl = "https://images.unsplash.com/photo-1577896851231-70ef18881754?auto=format&fit=crop&w=600&q=80",
        destination = Screen.Info.route
    ),
    CarouselItem(
        tag = "Comunidad",
        title = "Tu apoyo cambia vidas",
        description = "Súmate como voluntario o genera una alianza RSE con tu empresa.",
        buttonText = "Sumarme",
        imageUrl = "https://images.unsplash.com/photo-1559027615-cd4628902d4a?auto=format&fit=crop&w=600&q=80",
        destination = Screen.Registro.route
    )
)