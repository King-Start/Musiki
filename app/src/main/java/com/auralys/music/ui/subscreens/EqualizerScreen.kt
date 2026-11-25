package com.auralys.music.ui.subscreens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.auralys.music.ui.theme.AccentBlack
import com.auralys.music.ui.theme.CardSurface
import com.auralys.music.ui.theme.TextPrimary
import com.auralys.music.ui.theme.TextSecondary

@Composable
fun EqualizerScreen(nav: NavController) {
    SubScaffold(
        title = "Ekualiser",
        nav = nav,
        trailing = {
            Icon(Icons.Rounded.Add, "Tambah", tint = TextPrimary, modifier = Modifier.size(26.dp))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(color = CardSurface, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(14.dp)) {
                    Row {
                        Text("2,5", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                    EqGraph(Modifier.fillMaxWidth().height(140.dp))
                    Row(Modifier.fillMaxWidth()) {
                        Text("-2,5", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.weight(1f))
                        Text("100", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.weight(1f))
                        Text("1k", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.weight(1f))
                        Text("10k", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                }
            }

            Spacer(Modifier.height(18.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = true,
                    onClick = { },
                    colors = RadioButtonDefaults.colors(selectedColor = AccentBlack)
                )
                Spacer(Modifier.width(10.dp))
                Text("Dinonaktifkan", style = MaterialTheme.typography.titleMedium, color = TextPrimary)
            }

            Spacer(Modifier.height(60.dp))
            Icon(
                Icons.Rounded.Equalizer,
                null,
                tint = Color(0xFF5F6368),
                modifier = Modifier.size(44.dp)
            )
            Spacer(Modifier.height(14.dp))
            Text(
                "Tidak ada profil ekualiser",
                style = MaterialTheme.typography.titleMedium,
                color = TextSecondary
            )
            Spacer(Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .height(52.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(AccentBlack)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Impor profil",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 28.dp)
                )
            }
        }
    }
}

@Composable
private fun EqGraph(modifier: Modifier = Modifier) {
    Canvas(modifier) {
        val grid = Color(0xFFE1E3E7)
        val line = Color(0xFF17181B)
        val fill = Color(0xFFE4E6EA)
        val third = size.width / 3f
        // garis vertikal
        for (i in 1..2) {
            drawLine(grid, Offset(third * i, 0f), Offset(third * i, size.height), 2f)
        }
        drawLine(grid, Offset(0f, 0f), Offset(size.width, 0f), 2f)
        // area bawah garis nol
        val zero = size.height * 0.18f
        drawRect(fill, Offset(0f, zero), Size(size.width, size.height - zero))
        // garis datar tebal di posisi 0
        drawLine(line, Offset(0f, zero), Offset(size.width, zero), 6f)
    }
}
