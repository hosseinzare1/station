package ir.romina.hossein.features.map.presentation.widgets

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.romina.hossein.features.map.domain.entities.StationEntity

@Composable
fun StationCardView(
    station: StationEntity,
    isSelected: Boolean,
    onNavigationTap: () -> Unit,
    onDetailsTap: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.then(
            if (isSelected) {
                Modifier.border(2.dp, Color.Blue, RoundedCornerShape(8.dp))
            } else {
                Modifier
            }
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                //TODO set style form Theme class
                text = station.name ?: "", style = TextStyle(
                    fontSize = 22.sp
                )
            )
            Text(text = (station.capacity).toString())
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(onClick = onNavigationTap) {
                    Text(text = "Navigate")
                }
                Button(onClick = onDetailsTap) {
                    Text(text = "Details")
                }

            }
        }
    }
}