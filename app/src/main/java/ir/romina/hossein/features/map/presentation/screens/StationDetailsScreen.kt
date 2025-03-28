package ir.romina.hossein.features.map.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import ir.romina.hossein.features.map.domain.entities.StationEntity

@Composable
fun StationDetailsScreen(
    station: StationEntity,
) {
    Box {
        Column {
            Text(
                text = station.name ?: "", style = TextStyle(
                    fontSize = 22.sp
                )
            )
            Text(
                text = station.capacity.toString() ?: "", style = TextStyle(
                    fontSize = 22.sp
                )
            )
        }
    }
}
