package cl.duoc.corpaliv_grupo9.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.ui.graphics.vector.ImageVector

private const val IMAGE_SIZE = "auto=format&fit=crop&w=1200&q=85"

data class InfoProgram(
    val isEmployment: Boolean,
    val category: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val icon: ImageVector
)

data class InfoImpactMetric(
    val value: String,
    val label: String,
    val icon: ImageVector,
    val growth: String? = null
)

data class InfoStory(
    val category: String,
    val statement: String,
    val attribution: String,
    val note: String
)

object InfoScreenContent {
    val programs = listOf(
        InfoProgram(
            isEmployment = false,
            category = "Formación",
            title = "Escuela Especial Jan Van Dijk",
            description = "Educación y formación para niños y jóvenes con discapacidad múltiple.",
            imageUrl = "https://images.unsplash.com/photo-1577896851231-70ef18881754?$IMAGE_SIZE",
            icon = Icons.Default.School
        ),
        InfoProgram(
            isEmployment = true,
            category = "Empleo",
            title = "Red Sociolaboral Alba Lab",
            description = "Preparación para el empleo, producción y venta de productos hechos en los talleres.",
            imageUrl = "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?$IMAGE_SIZE",
            icon = Icons.Default.WorkOutline
        )
    )

    val youngPeopleInTraining = InfoImpactMetric(
        value = "120",
        label = "Jóvenes en formación",
        icon = Icons.Default.Groups
    )

    val secondaryMetrics = listOf(
        InfoImpactMetric(
            value = "15",
            label = "Productos en catálogo",
            icon = Icons.Default.Storefront,
            growth = "+12"
        ),
        InfoImpactMetric(
            value = "8",
            label = "Empresas aliadas",
            icon = Icons.Default.WorkOutline,
            growth = "+5"
        )
    )

    val stories = listOf(
        InfoStory(
            category = "Confección",
            statement = "Una joven del taller de confección crea piezas que pueden formar parte de nuestro catálogo.",
            attribution = "Participante, taller de confección",
            note = "Historia ilustrativa"
        ),
        InfoStory(
            category = "Formación",
            statement = "Cada nuevo aprendizaje abre oportunidades para participar y desarrollar más autonomía.",
            attribution = "Participante, programa de formación",
            note = "Historia ilustrativa"
        )
    )
}
