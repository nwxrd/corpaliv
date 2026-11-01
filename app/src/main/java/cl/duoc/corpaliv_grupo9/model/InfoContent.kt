package cl.duoc.corpaliv_grupo9.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.ui.graphics.vector.ImageVector

private const val IMAGE_SIZE = "auto=format&fit=crop&w=1200&q=85"

enum class InfoProgramType {
    EDUCATION,
    EMPLOYMENT
}

data class InfoProgram(
    val type: InfoProgramType,
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
    val note: String,
    val imageUrl: String
)

object InfoScreenContent {
    val programs = listOf(
        InfoProgram(
            type = InfoProgramType.EDUCATION,
            category = "Formación",
            title = "Escuela Especial Jan Van Dijk",
            description = "Educación y formación para niños y jóvenes con discapacidad múltiple.",
            imageUrl = "https://images.unsplash.com/photo-1509062522246-3755977927d7?$IMAGE_SIZE",
            icon = Icons.Default.School
        ),
        InfoProgram(
            type = InfoProgramType.EMPLOYMENT,
            category = "Empleo",
            title = "Red Sociolaboral Alba Lab",
            description = "Preparación para el empleo, producción y venta de productos hechos en los talleres.",
            imageUrl = "https://images.unsplash.com/photo-1513364776144-60967b0f800f?$IMAGE_SIZE",
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

    val story = InfoStory(
        category = "Confección",
        statement = "Una joven del taller de confección hoy produce piezas que se venden en nuestro catálogo.",
        attribution = "Participante, taller de confección",
        note = "Caso de ejemplo anonimizado",
        imageUrl = "https://images.unsplash.com/photo-1455390582262-044cdead277a?$IMAGE_SIZE"
    )
}
