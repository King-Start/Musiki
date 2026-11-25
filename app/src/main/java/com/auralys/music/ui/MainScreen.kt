package com.auralys.music.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home as HomeOutlined
import androidx.compose.material.icons.outlined.Person as PersonOutlined
import androidx.compose.material.icons.outlined.Search as SearchOutlined
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.LibraryMusic
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.auralys.music.data.MusicRepository
import com.auralys.music.ui.components.SongCover
import com.auralys.music.ui.home.HomeScreen
import com.auralys.music.ui.library.LibraryScreen
import com.auralys.music.ui.player.NowPlayingScreen
import com.auralys.music.ui.profile.ProfileScreen
import com.auralys.music.ui.search.SearchScreen
import com.auralys.music.ui.subscreens.EqualizerScreen
import com.auralys.music.ui.subscreens.HistoryScreen
import com.auralys.music.ui.subscreens.ListScreen
import com.auralys.music.ui.subscreens.PlayerAudioScreen
import com.auralys.music.ui.subscreens.RecognizeScreen
import com.auralys.music.ui.subscreens.SettingsScreen
import com.auralys.music.ui.subscreens.StatsScreen
import com.auralys.music.ui.subscreens.TogetherScreen
import com.auralys.music.ui.theme.AppBackground
import com.auralys.music.ui.theme.NavSurface
import com.auralys.music.ui.theme.PillActive
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val LIBRARY = "library"
    const val PROFILE = "profile"
    const val NOW_PLAYING = "now_playing"
    const val HISTORY = "history"
    const val STATS = "stats"
    const val TOGETHER = "together"
    const val RECOGNIZE = "recognize"
    const val EQUALIZER = "equalizer"
    const val SETTINGS = "settings"
    const val PLAYER_SETTINGS = "player_settings"
    const val LIST = "list/{type}"
    fun list(type: String) = "list/$type"
}

private val tabRoutes = listOf(Routes.HOME, Routes.SEARCH, Routes.LIBRARY, Routes.PROFILE)

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val backStack by navController.currentBackStackEntryAsState()
    val route = backStack?.destination?.route ?: Routes.HOME
    val isTab = tabRoutes.contains(route)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(Routes.HOME) { HomeScreen(nav = navController) }
            composable(Routes.SEARCH) { SearchScreen(nav = navController) }
            composable(Routes.LIBRARY) { LibraryScreen(nav = navController) }
            composable(Routes.PROFILE) { ProfileScreen(nav = navController) }
            composable(Routes.NOW_PLAYING) { NowPlayingScreen(nav = navController) }
            composable(Routes.HISTORY) { HistoryScreen(nav = navController) }
            composable(Routes.STATS) { StatsScreen(nav = navController) }
            composable(Routes.TOGETHER) { TogetherScreen(nav = navController) }
            composable(Routes.RECOGNIZE) { RecognizeScreen(nav = navController) }
            composable(Routes.EQUALIZER) { EqualizerScreen(nav = navController) }
            composable(Routes.SETTINGS) { SettingsScreen(nav = navController) }
            composable(Routes.PLAYER_SETTINGS) { PlayerAudioScreen(nav = navController) }
            composable(Routes.LIST) { entry ->
                ListScreen(
                    type = entry.arguments?.getString("type") ?: "disukai",
                    nav = navController
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 12.dp)
                .padding(bottom = 10.dp)
        ) {
            MiniPlayerBar(nav = navController)
            if (isTab) {
                Spacer(Modifier.height(10.dp))
                BottomNavBar(currentRoute = route, nav = navController)
            }
        }
    }
}

@Composable
fun MiniPlayerBar(nav: NavHostController) {
    val current by MusicRepository.currentSong.collectAsStateWithLifecycle()
    val playing by MusicRepository.isPlaying.collectAsStateWithLifecycle()
    val liked by MusicRepository.likedIds.collectAsStateWithLifecycle()
    val position by MusicRepository.positionMs.collectAsStateWithLifecycle()
    val duration by MusicRepository.durationMs.collectAsStateWithLifecycle()
    val song = current ?: return

    val fraction = if (duration > 0) (position.toFloat() / duration.toFloat()).coerceIn(0f, 1f) else 0f
    val isLiked = liked.contains(song.id)

    Column {
        Surface(
            color = NavSurface,
            shape = RoundedCornerShape(26.dp),
            shadowElevation = 10.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { nav.navigate(Routes.NOW_PLAYING) }
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Color(0xFFD7D9DD))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction)
                            .height(2.dp)
                            .background(TextSecondary)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SongCover(song = song, modifier = Modifier.size(44.dp), cornerRadius = 8.dp)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = song.title,
                            style = MaterialBoldTitle,
                            color = TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = song.artist,
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Icon(
                        imageVector = if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Suka",
                        tint = TextPrimary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { MusicRepository.toggleLike(song.id) }
                    )
                    Spacer(Modifier.width(14.dp))
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(com.auralys.music.ui.theme.AccentBlack)
                            .clickable { MusicRepository.togglePlay() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (playing) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = "Putar/Jeda",
                            tint = Color.White,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.SkipNext,
                        contentDescription = "Berikutnya",
                        tint = TextPrimary,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { MusicRepository.next() }
                    )
                }
            }
        }
    }
}

private val MaterialBoldTitle
    @Composable
    get() = androidx.compose.material3.MaterialTheme.typography.titleMedium.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )

private data class NavItem(
    val route: String,
    val label: String,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
)

private val navItems = listOf(
    NavItem(Routes.HOME, "Beranda", Icons.Rounded.Home, Icons.Outlined.HomeOutlined),
    NavItem(Routes.SEARCH, "Cari", Icons.Rounded.Search, Icons.Outlined.SearchOutlined),
    NavItem(Routes.LIBRARY, "Pustaka", Icons.Rounded.LibraryMusic, Icons.Rounded.LibraryMusic),
    NavItem(Routes.PROFILE, "Kamu", Icons.Rounded.Person, Icons.Outlined.PersonOutlined)
)

@Composable
fun BottomNavBar(currentRoute: String, nav: NavHostController) {
    Surface(
        color = NavSurface,
        shape = RoundedCornerShape(32.dp),
        shadowElevation = 10.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            navItems.forEach { item ->
                val selected = currentRoute == item.route
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clip(RoundedCornerShape(26.dp))
                        .background(if (selected) PillActive else Color.Transparent)
                        .clickable {
                            if (!selected) {
                                nav.navigate(item.route) {
                                    popUpTo(Routes.HOME) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                        .padding(horizontal = 22.dp, vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = if (selected) item.activeIcon else item.inactiveIcon,
                        contentDescription = item.label,
                        tint = if (selected) TextPrimary else TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        fontSize = 11.sp,
                        color = if (selected) TextPrimary else TextSecondary
                    )
                }
            }
        }
    }
}
