package com.maxrave.simpmusic.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.maxrave.domain.manager.DataStoreManager
import com.maxrave.domain.manager.DataStoreManager.Values.TRUE
import com.maxrave.simpmusic.ui.icon.ArrowForwardIos
import com.maxrave.simpmusic.ui.icon.Groups
import com.maxrave.simpmusic.ui.icon.Info
import com.maxrave.simpmusic.ui.icon.Insights
import com.maxrave.simpmusic.ui.icon.Sensors
import com.maxrave.simpmusic.ui.icon.Settings
import com.maxrave.simpmusic.ui.icon.SimpIcons
import com.maxrave.simpmusic.ui.navigation.destination.home.AnalyticsDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.CreditDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.ListenTogetherDestination
import com.maxrave.simpmusic.ui.navigation.destination.home.SettingsDestination
import com.maxrave.simpmusic.ui.navigation.destination.library.MixForYouDestination
import com.maxrave.simpmusic.viewModel.SharedViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import simpmusic.composeapp.generated.resources.Res
import simpmusic.composeapp.generated.resources.about
import simpmusic.composeapp.generated.resources.analytics
import simpmusic.composeapp.generated.resources.listen_together
import simpmusic.composeapp.generated.resources.mix_for_you
import simpmusic.composeapp.generated.resources.settings
import simpmusic.composeapp.generated.resources.you

/**
 * "Kamu" tab. Rows are grouped on rounded cards, the same way the reference design lays out its
 * You page; every row opens a screen that already exists in the app.
 */
@Composable
fun YouScreen(
    navController: NavController,
    innerPadding: PaddingValues,
    sharedViewModel: SharedViewModel = koinInject(),
) {
    val localTracking by sharedViewModel.getLocalTrackingEnabled().collectAsStateWithLifecycle(DataStoreManager.FALSE)
    val youTubeLoggedIn by sharedViewModel.getYouTubeLoggedIn().collectAsStateWithLifecycle(DataStoreManager.FALSE)

    val insightRows =
        buildList {
            if (localTracking == TRUE) {
                add(YouRow(SimpIcons.Insights, stringResource(Res.string.analytics)) { navController.navigate(AnalyticsDestination) })
            }
            if (youTubeLoggedIn == TRUE) {
                add(YouRow(SimpIcons.Sensors, stringResource(Res.string.mix_for_you)) { navController.navigate(MixForYouDestination) })
            }
            add(YouRow(SimpIcons.Groups, stringResource(Res.string.listen_together)) { navController.navigate(ListenTogetherDestination) })
        }
    val appRows =
        listOf(
            YouRow(SimpIcons.Settings, stringResource(Res.string.settings)) { navController.navigate(SettingsDestination) },
            YouRow(SimpIcons.Info, stringResource(Res.string.about)) { navController.navigate(CreditDestination) },
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(Res.string.you),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp, start = 4.dp),
        )
        YouCard(insightRows)
        YouCard(appRows)
        Spacer(Modifier.height(24.dp))
    }
}

private class YouRow(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)

@Composable
private fun YouCard(rows: List<YouRow>) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceContainerLowest),
    ) {
        rows.forEachIndexed { index, row ->
            if (index > 0) {
                HorizontalDivider(
                    modifier = Modifier.padding(start = 56.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable(onClick = row.onClick)
                        .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Icon(row.icon, contentDescription = null, modifier = Modifier.size(22.dp), tint = MaterialTheme.colorScheme.onSurface)
                Text(
                    text = row.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f),
                )
                Icon(
                    SimpIcons.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
