package com.auralys.music.ui.subscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Group
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.ChipSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun TogetherScreen(nav: NavController) {
    var connected by rememberSaveable { mutableStateOf(false) }
    var username by rememberSaveable { mutableStateOf("") }
    var sessionCode by rememberSaveable { mutableStateOf("") }

    SubScaffold(title = "Bersama", nav = nav) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .padding(bottom = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .size(104.dp)
                    .clip(CircleShape)
                    .background(ChipSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.Group, null, tint = AccentBlack, modifier = Modifier.size(48.dp))
            }
            Spacer(Modifier.height(20.dp))
            Text(
                "Dengar bersama",
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary
            )
            Spacer(Modifier.height(10.dp))
            Text(
                "Dengarkan musik bersama teman secara real time. Buat sesi untuk menjadi host, atau gabung ke sesi yang ada dengan kode",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))

            Surface(color = CardSurface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(if (connected) Color(0xFF34A853) else Color(0xFF5F6368))
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            if (connected) "Terhubung" else "Terputus",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextPrimary
                        )
                    }
                    Spacer(Modifier.height(14.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(28.dp))
                            .background(AccentBlack)
                            .clickable { connected = !connected },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Rounded.Link, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(10.dp))
                            Text(
                                if (connected) "Putuskan" else "Hubungkan",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Surface(color = CardSurface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    OutlineField(
                        icon = { Icon(Icons.Rounded.Person, null, tint = AccentBlack, modifier = Modifier.size(22.dp)) },
                        value = username,
                        hint = "Nama pengguna",
                        onChange = { username = it }
                    )
                    Spacer(Modifier.height(14.dp))
                    OutlineField(
                        icon = { Icon(Icons.Rounded.Group, null, tint = AccentBlack, modifier = Modifier.size(22.dp)) },
                        value = sessionCode,
                        hint = "Kode sesi",
                        onChange = { sessionCode = it }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Surface(color = CardSurface, shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Settings, null, tint = AccentBlack, modifier = Modifier.size(26.dp))
                    Spacer(Modifier.width(16.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Pengaturan", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
                        Text(
                            "Konfigurasikan server, nama pengguna, dan lainnya",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                    Icon(Icons.Rounded.ArrowForward, null, tint = TextPrimary, modifier = Modifier.size(22.dp))
                }
            }
        }
    }
}

@Composable
private fun OutlineField(
    icon: @Composable () -> Unit,
    value: String,
    hint: String,
    onChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Transparent)
            .then(
                Modifier.drawOutline()
            )
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(Modifier.width(12.dp))
        androidx.compose.foundation.text.BasicTextField(
            value = value,
            onValueChange = onChange,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.titleMedium.copy(color = TextPrimary),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (value.isEmpty()) {
                        Text(hint, style = MaterialTheme.typography.titleMedium, color = TextSecondary)
                    }
                    inner()
                }
            }
        )
    }
}

private fun Modifier.drawOutline(): Modifier = this.drawBehind {
    drawRect(
        color = androidx.compose.ui.graphics.Color(0xFFB9BCC2),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 3f)
    )
}
