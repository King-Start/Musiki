package com.auralys.music.ui.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auralys.music.ui.Routes
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.DividerColor
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun ProfileScreen(nav: NavController) {
    val vm: ProfileViewModel = viewModel()
    val sync by vm.syncAccount.collectAsStateWithLifecycle(initialValue = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 220.dp)
    ) {
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Kamu",
            style = MaterialTheme.typography.headlineSmall,
            color = TextPrimary,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Spacer(Modifier.height(24.dp))

        GroupCard {
            MenuRow(Icons.Rounded.History, "Riwayat") { nav.navigate(Routes.HISTORY) }
            DividerLine()
            MenuRow(Icons.Rounded.BarChart, "Statistik dan recap") { nav.navigate(Routes.STATS) }
        }
        Spacer(Modifier.height(16.dp))

        GroupCard {
            MenuRow(Icons.Rounded.Group, "Dengar bersama") { nav.navigate(Routes.TOGETHER) }
            DividerLine()
            MenuRow(Icons.Rounded.Mic, "Kenali lagu") { nav.navigate(Routes.RECOGNIZE) }
            DividerLine()
            MenuRow(Icons.Rounded.Equalizer, "Ekualiser") { nav.navigate(Routes.EQUALIZER) }
        }
        Spacer(Modifier.height(24.dp))

        Text(
            text = "YouTube Music",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Spacer(Modifier.height(8.dp))
        GroupCard {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Person, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(18.dp))
                Column {
                    Text("Bukan Bukan", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                    Text(
                        "Sinkronkan lagu disukai, pustaka, dan playlist",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
            DividerLine()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Sync, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(18.dp))
                Text(
                    "Sinkronisasi otomatis dengan akun",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = sync,
                    onCheckedChange = { vm.setSync(it) },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = AccentBlack,
                        checkedThumbColor = CardSurface,
                        uncheckedTrackColor = DividerColor,
                        uncheckedThumbColor = CardSurface
                    )
                )
            }
            DividerLine()
            MenuRow(Icons.Rounded.Logout, "Keluar") { }
        }
        Spacer(Modifier.height(16.dp))

        GroupCard {
            MenuRow(Icons.Rounded.Settings, "Pengaturan") { nav.navigate(Routes.SETTINGS) }
            DividerLine()
            MenuRow(Icons.Rounded.Info, "Tentang") { nav.navigate(Routes.SETTINGS) }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun GroupCard(content: @Composable () -> Unit) {
    Surface(
        color = CardSurface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(0.dp)) { content() }
    }
}

@Composable
fun DividerLine() {
    HorizontalDivider(
        color = DividerColor,
        thickness = 1.dp,
        modifier = Modifier.padding(horizontal = 20.dp)
    )
}

@Composable
fun MenuRow(
    icon: ImageVector,
    label: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(18.dp))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
        }
        Icon(Icons.Rounded.ChevronRight, null, tint = TextSecondary, modifier = Modifier.size(20.dp))
    }
}
