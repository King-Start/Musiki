package com.maxrave.simpmusic.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import org.jetbrains.compose.resources.stringResource
import kotlin.reflect.KClass

private val PillTabs =
    listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Search,
        BottomNavScreen.Library,
        BottomNavScreen.You,
    )

/**
 * The phone bottom bar of the redesign: one floating, translucent pill holding four labelled
 * tabs (Beranda, Cari, Pustaka, Kamu). The selected tab sits on a soft rounded highlight.
 */
@Composable
fun PillNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier,
    reloadDestinationIfNeeded: (KClass<*>) -> Unit = { _ -> },
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    var selectedOrdinal by rememberSaveable { mutableIntStateOf(BottomNavScreen.Home.ordinal) }

    // Follow the back stack: opening a tab by any route (a deep link, back from a detail page)
    // moves the highlight with it. Detail screens match no tab and keep the last one lit.
    LaunchedEffect(currentDestination) {
        PillTabs
            .firstOrNull { tab ->
                currentDestination?.hierarchy?.any { it.hasRoute(tab.destination::class) } == true
            }?.let { selectedOrdinal = it.ordinal }
    }

    val onTabClick: (BottomNavScreen) -> Unit = { screen ->
        val onThisTab =
            currentDestination?.hierarchy?.any { it.hasRoute(screen.destination::class) } == true
        if (onThisTab) {
            reloadDestinationIfNeeded(screen.destination::class)
        } else {
            selectedOrdinal = screen.ordinal
            navController.navigate(screen.destination) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                .padding(horizontal = 16.dp)
                .padding(top = 4.dp, bottom = 8.dp)
                .height(64.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerLowest.copy(alpha = 0.94f),
        shadowElevation = 6.dp,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(6.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            PillTabs.forEach { tab ->
                val selected = tab.ordinal == selectedOrdinal
                val tint =
                    if (selected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                Column(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(
                                if (selected) MaterialTheme.colorScheme.surfaceContainerHigh else androidx.compose.ui.graphics.Color.Transparent,
                            ).clickable { onTabClick(tab) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    CompositionLocalProvider(LocalContentColor provides tint) {
                        Column(
                            modifier = Modifier.size(22.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) { tab.icon() }
                        Text(
                            text = stringResource(tab.title),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
                            color = tint,
                            maxLines = 1,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                    }
                }
            }
        }
    }
}
