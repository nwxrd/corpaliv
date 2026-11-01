package cl.duoc.corpaliv_grupo9.model

import androidx.compose.ui.graphics.Color

data class CarouselItem (
    val tag: String,
    val tagBgColor: Color,
    val tagTextColor: Color,
    val title: String,
    val description: String,
    val buttonText: String,
    val buttonBgColor: Color,
    val buttonTextColor: Color,
    val imageUrl: String,
    val gradientStartColor: Color,
    val onClick: () -> Unit
)