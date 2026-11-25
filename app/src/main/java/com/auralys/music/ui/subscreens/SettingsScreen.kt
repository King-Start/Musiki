package com.auralys.music.ui.subscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.DirectionsCar
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.Translate
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
import com.auralys.music.data.Prefs
import com.auralys.music.ui.Routes
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.DividerColor
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun SettingsScreen(nav: NavController) {
    val vm: SettingsViewModel = viewModel()
    val settings by vm.settings.collectAsStateWithLifecycle()

    SubScaffold(title = "Pengaturan", nav = nav) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 200.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Rounded.Search, null, tint = TextSecondary, modifier = Modifier.size(22.dp))
                Spacer(Modifier.width(14.dp))
                Text("Cari pengaturan…", style = MaterialTheme.typography.titleMedium, color = TextSecondary)
            }

            Spacer(Modifier.height(16.dp))
            SectionLabel("YouTube Music")
            SettingsCard {
                SettingRow(Icons.Rounded.Person, "Bukan Bukan", "Sinkronkan lagu disukai, pustaka, dan playlist") { }
                DividerLine2()
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
                        checked = settings.syncAccount,
                        onCheckedChange = { vm.setBool(Prefs.SYNC_ACCOUNT, it) },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = AccentBlack,
                            checkedThumbColor = CardSurface,
                            uncheckedTrackColor = DividerColor,
                            uncheckedThumbColor = CardSurface
                        )
                    )
                }
                DividerLine2()
                SettingRow(Icons.Rounded.Logout, "Keluar", null) { }
            }

            Spacer(Modifier.height(16.dp))
            SettingsCard {
                SettingRow(Icons.Rounded.PlayArrow, "Suara dan pemutaran", null) { nav.navigate(Routes.PLAYER_SETTINGS) }
                DividerLine2()
                SettingRow(Icons.Rounded.Palette, "Tampilan", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.Language, "Bahasa dan konten", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.Translate, "Lirik dan terjemahan", null) { }
            }

            Spacer(Modifier.height(16.dp))
            SettingsCard {
                SettingRow(Icons.Rounded.Security, "Privasi dan riwayat", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.Storage, "Penyimpanan", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.History, "Cadangan dan impor", null) { }
            }

            Spacer(Modifier.height(16.dp))
            SettingsCard {
                SettingRow(Icons.Rounded.DirectionsCar, "Android Auto", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.Link, "Tautan", null) { }
                DividerLine2()
                SettingRow(Icons.Rounded.Info, "Tentang Auralys", null) { }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun SectionLabel(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = TextSecondary,
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 6.dp)
    )
}

@Composable
fun SettingsCard(content: @Composable () -> Unit) {
    Surface(
        color = CardSurface,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Column { content() }
    }
}

@Composable
fun DividerLine2() {
    HorizontalDivider(color = DividerColor, thickness = 1.dp, modifier = Modifier.padding(horizontal = 20.dp))
}

@Composable
fun SettingRow(
    icon: ImageVector,
    label: String,
    subtitle: String?,
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
