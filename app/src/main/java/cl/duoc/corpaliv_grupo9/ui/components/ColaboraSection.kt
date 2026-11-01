package cl.duoc.corpaliv_grupo9.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.corpaliv_grupo9.ui.theme.HomeIconViolet
import cl.duoc.corpaliv_grupo9.ui.theme.HomePrimary
import cl.duoc.corpaliv_grupo9.ui.theme.HomeSwitchTrack
import cl.duoc.corpaliv_grupo9.ui.theme.HomeText
import cl.duoc.corpaliv_grupo9.ui.theme.HomeTextSecondary
import cl.duoc.corpaliv_grupo9.ui.theme.White

private data class HelpOption(
    val title: String,
    val description: String,
    val action: String,
    val icon: ImageVector,
    val iconBackground: Color,
    val iconTint: Color
)

private val personOptions = listOf(
    HelpOption(
        title = "Donar",
        description = "Apoya talleres y formación",
        action = "Donar Persona",
        icon = Icons.Default.Favorite,
        iconBackground = HomePrimary,
        iconTint = White
    ),
    HelpOption(
        title = "Comprar en Alba Lab",
        description = "Productos hechos en nuestros talleres",
        action = "Alba Lab",
        icon = Icons.Default.ShoppingBag,
        iconBackground = HomePrimary,
        iconTint = White
    ),
    HelpOption(
        title = "Hacer voluntariado",
        description = "Participa en actividades de la escuela",
        action = "Voluntariado",
        icon = Icons.Default.VolunteerActivism,
        iconBackground = HomeIconViolet,
        iconTint = White
    )
)

private val companyOptions = listOf(
    HelpOption(
        title = "Crear una alianza RSE",
        description = "Abre oportunidades laborales inclusivas",
        action = "Alianza RSE",
        icon = Icons.Default.Handshake,
        iconBackground = HomeIconViolet,
        iconTint = White
    ),
    HelpOption(
        title = "Capacitar a tu equipo",
        description = "Conoce nuestros talleres y programas",
        action = "Capacitaciones",
        icon = Icons.Default.School,
        iconBackground = HomePrimary,
        iconTint = White
    ),
    HelpOption(
        title = "Apoyar con una donación",
        description = "Aporta a los programas de inclusión",
        action = "Donar Empresa",
        icon = Icons.Default.Favorite,
        iconBackground = HomePrimary,
        iconTint = White
    )
)

@Composable
fun ColaboraSection(
    modifier: Modifier = Modifier,
    onOptionClick: (String) -> Unit = {}
) {
    var isCompanySelected by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = "FORMAS DE PARTICIPAR",
            color = HomePrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.1.sp
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "¿Cómo quieres ayudar?",
            color = HomeText,
            fontSize = 24.sp,
            lineHeight = 29.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = "Elige la forma que mejor se adapte a ti.",
            color = HomeTextSecondary,
            fontSize = 14.sp,
            lineHeight = 19.sp
        )
        Spacer(Modifier.height(16.dp))

        AudienceSelector(
            isCompanySelected = isCompanySelected,
            onSelectionChange = { isCompanySelected = it }
        )
        Spacer(Modifier.height(12.dp))

        AnimatedVisibility(
            visible = !isCompanySelected,
            enter = fadeIn() + expandVertically(),
            exit = shrinkVertically() + fadeOut()
        ) {
            HelpOptionList(personOptions, onOptionClick)
        }
        AnimatedVisibility(
            visible = isCompanySelected,
            enter = fadeIn() + expandVertically(),
            exit = shrinkVertically() + fadeOut()
        ) {
            HelpOptionList(companyOptions, onOptionClick)
        }
    }
}

@Composable
private fun AudienceSelector(
    isCompanySelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = CircleShape,
        color = HomeSwitchTrack
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AudienceOption(
                label = "Persona",
                icon = Icons.Default.Person,
                selected = !isCompanySelected,
                onClick = { onSelectionChange(false) },
                modifier = Modifier.weight(1f)
            )
            AudienceOption(
                label = "Empresa",
                icon = Icons.Default.Business,
                selected = isCompanySelected,
                onClick = { onSelectionChange(true) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun AudienceOption(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(42.dp)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick),
        shape = CircleShape,
        color = if (selected) White else HomeSwitchTrack,
        shadowElevation = if (selected) 2.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (selected) HomePrimary else HomeTextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = label,
                color = if (selected) HomePrimary else HomeTextSecondary,
                fontSize = 14.sp,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
            )
        }
    }
}

@Composable
private fun HelpOptionList(
    options: List<HelpOption>,
    onOptionClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        options.forEachIndexed { index, option ->
            HelpOptionRow(
                option = option,
                featured = index == 0,
                onClick = { onOptionClick(option.action) }
            )
        }
    }
}

@Composable
private fun HelpOptionRow(
    option: HelpOption,
    featured: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = if (featured) HomePrimary else White),
        elevation = CardDefaults.cardElevation(defaultElevation = if (featured) 2.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 78.dp)
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        if (featured) White.copy(alpha = 0.18f) else option.iconBackground,
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = option.icon,
                    contentDescription = null,
                    tint = if (featured) White else option.iconTint,
                    modifier = Modifier.size(23.dp)
                )
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = option.title,
                    color = if (featured) White else HomeText,
                    fontSize = 15.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = option.description,
                    color = if (featured) White.copy(alpha = 0.88f) else HomeTextSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(if (featured) White else HomeSwitchTrack, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = HomePrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}