package cl.duoc.corpaliv_grupo9.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cl.duoc.corpaliv_grupo9.model.InfoScreenContent
import cl.duoc.corpaliv_grupo9.ui.components.InfoHeroSection
import cl.duoc.corpaliv_grupo9.ui.components.InfoImpactSection
import cl.duoc.corpaliv_grupo9.ui.components.InfoJoinSection
import cl.duoc.corpaliv_grupo9.ui.components.InfoProgramsSection
import cl.duoc.corpaliv_grupo9.ui.components.InfoStorySection

@Composable
fun InfoScreen(
    onNavigateToCatalog: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .verticalScroll(rememberScrollState())
    ) {
        InfoHeroSection()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-28).dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(colors.background)
                .padding(top = 28.dp, bottom = 24.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 18.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                InfoProgramsSection(
                    programs = InfoScreenContent.programs,
                    onEmploymentClick = onNavigateToCatalog
                )
                InfoImpactSection(
                    lead = InfoScreenContent.youngPeopleInTraining,
                    metrics = InfoScreenContent.secondaryMetrics
                )
                InfoStorySection(stories = InfoScreenContent.stories)
                InfoJoinSection(onRegisterClick = onRegisterClick)
            }
        }
        Spacer(Modifier.height(8.dp))
    }
}
