package com.auralys.music.ui.subscreens

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.QueueMusic
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.material.icons.rounded.TimerOff
import androidx.compose.material.icons.rounded.HourglassEmpty
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.auralys.music.data.Prefs
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.DividerColor
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun PlayerAudioScreen(nav: NavController) {
    val vm: SettingsViewModel = viewModel()
    val s by vm.settings.collectAsStateWithLifecycle()

    SubScaffold(title = "Pemutar dan audio", nav = nav) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 200.dp)
        ) {
            SectionLabel("Pemutar")
            SettingsCard {
                SettingRow(Icons.Rounded.Equalizer, "Kualitas audio", "Otomatis") { }
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.QueueMusic,
                    label = "Mode hemat data",
                    subtitle = "Memutar dengan kualitas audio rendah, tanpa sampul bergerak, dan tanpa unduhan sampul di latar. Lagu yang kamu unduh tetap memakai kualitas pilihanmu.",
                    checked = s.dataSaver,
                    onChecked = { vm.setBool(Prefs.DATA_SAVER, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.Equalizer,
                    label = "Crossfade",
                    subtitle = null,
                    checked = s.crossfade,
                    onChecked = { vm.setBool(Prefs.CROSSFADE, it) }
                )
                DividerLine2()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.History, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(18.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Durasi riwayat", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text("${s.historyDays}", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        Slider(
                            value = s.historyDays.toFloat(),
                            onValueChange = { vm.setInt(Prefs.HISTORY_DAYS, it.toInt()) },
                            valueRange = 1f..90f,
                            colors = SliderDefaults.colors(
                                thumbColor = CardSurface,
                                activeTrackColor = TextSecondary,
                                inactiveTrackColor = DividerColor
                            )
                        )
                    }
                }
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.PlayArrow,
                    label = "Lewati keheningan",
                    subtitle = null,
                    checked = s.skipSilence,
                    onChecked = { vm.setBool(Prefs.SKIP_SILENCE, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.SkipNext,
                    label = "Lewati keheningan seketika",
                    subtitle = "Langsung melompati bagian hening alih-alih mempercepat pemutaran",
                    checked = s.skipSilenceInstant,
                    onChecked = { vm.setBool(Prefs.SKIP_SILENCE_INSTANT, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.VolumeUp,
                    label = "Normalisasi audio",
                    subtitle = null,
                    checked = s.normalization,
                    onChecked = { vm.setBool(Prefs.NORMALIZATION, it) }
                )
                DividerLine2()
                SettingRow(Icons.Rounded.VolumeUp, "Tingkat kelantangan", "Agresif (-7 dB)") { }
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.Equalizer,
                    label = "Aktifkan audio offload",
                    subtitle = "Menggunakan jalur audio offload perangkat keras untuk pemutaran. Menonaktifkan ini dapat meningkatkan penggunaan baterai, tetapi dapat membantu jika mengalami masalah pemutaran atau pemrosesan audio",
                    checked = s.audioOffload,
                    onChecked = { vm.setBool(Prefs.AUDIO_OFFLOAD, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.Equalizer,
                    label = "Aktifkan varispeed",
                    subtitle = "Kaitkan nada dengan kecepatan pemutaran, menghasilkan efek percepatan dan perlambatan ala kaset",
                    checked = s.varispeed,
                    onChecked = { vm.setBool(Prefs.VARISPEED, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.Equalizer,
                    label = "Gunakan pemrosesan audio perangkat keras",
                    subtitle = "Gunakan AudioTrack Android untuk mengubah kecepatan/nada, bukan pemrosesan perangkat lunak. Lebih andal untuk nilai nada yang ekstrem",
                    checked = s.hwProcessing,
                    onChecked = { vm.setBool(Prefs.HW_PROCESSING, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.ChevronRight,
                    label = "Lompatan progresif",
                    subtitle = "Saat aktif, setiap geseran posisi menambah 5 detik",
                    checked = s.progressiveSeek,
                    onChecked = { vm.setBool(Prefs.PROGRESSIVE_SEEK, it) }
                )
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Timer tidur")
            SettingsCard {
                ToggleRow(
                    icon = Icons.Rounded.Timer,
                    label = "Aktifkan timer tidur otomatis",
                    subtitle = null,
                    checked = s.sleepTimer,
                    onChecked = { vm.setBool(Prefs.SLEEP_TIMER, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.Repeat,
                    label = "Ulangi",
                    subtitle = null,
                    checked = s.sleepRepeat,
                    onChecked = { vm.setBool(Prefs.SLEEP_REPEAT, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.TimerOff,
                    label = "Berhenti di akhir lagu saat ini",
                    subtitle = null,
                    checked = s.sleepEndOfTrack,
                    onChecked = { vm.setBool(Prefs.SLEEP_END_OF_TRACK, it) }
                )
                DividerLine2()
                ToggleRow(
                    icon = Icons.Rounded.HourglassEmpty,
                    label = "Memudar",
                    subtitle = null,
                    checked = s.sleepFade,
                    onChecked = { vm.setBool(Prefs.SLEEP_FADE, it) }
                )
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Alarm")
            SettingsCard {
                SettingRow(Icons.Rounded.AddCircle, "Tambah alarm", null) { }
                DividerLine2()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.DarkMode, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(18.dp))
                    Text(
                        "Belum ada alarm. Tambahkan satu untuk mulai menjadwalkan playlist",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
                DividerLine2()
                SettingRow(
                    Icons.Rounded.Warning,
                    "Optimalisasi baterai",
                    "Nonaktifkan optimalisasi baterai agar alarm tetap berjalan di latar belakang"
                ) { }
            }

            Spacer(Modifier.height(20.dp))
            SectionLabel("Antrean")
            SettingsCard {
                SettingRow(Icons.Rounded.QueueMusic, "Antrean saat ini", null) { nav.navigate(com.auralys.music.ui.Routes.NOW_PLAYING) }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun ToggleRow(
    icon: ImageVector,
    label: String,
    subtitle: String?,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, null, tint = TextPrimary, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(18.dp))
        Column(Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            if (subtitle != null) {
                Spacer(Modifier.height(4.dp))
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
            }
        }
        Spacer(Modifier.width(12.dp))
        Switch(
            checked = checked,
            onCheckedChange = onChecked,
            colors = SwitchDefaults.colors(
                checkedTrackColor = AccentBlack,
                checkedThumbColor = CardSurface,
                uncheckedTrackColor = DividerColor,
                uncheckedThumbColor = CardSurface
            )
        )
    }
}
